package com.yibi.orderapi.biz.impl;

import com.google.gson.Gson;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.RechargeBiz;
import com.yibi.orderapi.biz.WalletBiz;
import com.yibi.orderapi.biz.walletUtil;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.dto.WithdrawModel;
import com.yibi.orderapi.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
@Slf4j
@Service
@Transactional
public class WalletBizImpl extends BaseBizImpl implements WalletBiz {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private UserService userService;
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private RechargeBiz rechargeBiz;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountChainService accountChainService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private RedisTemplate<String, String> redis;

    public static BigDecimal WEI = new BigDecimal("1000000000000000000");

    @Override
    public String queryByUser(User user, Integer accountType) {
        Map<String, Object> accountMap = new HashMap<String, Object>();
        List<Account> list = accountService.queryByUserIdAndAccountType(user.getId(), accountType);
        BigDecimal totalSumOfCny = new BigDecimal(0);
        List<Map<String, Object>> li = new ArrayList<>();
        for (Account account : list) {
            Map<String, Object> data = new HashMap<String, Object>();
            Integer coinType = account.getCointype();
            CoinManage coin = coinManageService.queryByCoinType(coinType);
            CoinScale coinScale = coinScaleService.queryByCoin(coinType, -1);
            BigDecimal availBalance = account.getAvailbalance();
            BigDecimal frozenBlance = account.getFrozenblance();
            BigDecimal totalBalance = BigDecimalUtils.add(availBalance, frozenBlance);
            BigDecimal totalOfCny = BigDecimalUtils.multiply(totalBalance, getPriceOfCNY(coinType));
            data.put("coinType", coinType);
            data.put("cnName", coin.getCnname());
            data.put("totalBalance", BigDecimalUtils.toStringInZERO(totalBalance, coinScale.getCalculscale()));
            data.put("totalOfCny", BigDecimalUtils.toStringInZERO(totalOfCny, coinScale.getAvailofcnyscale()));
            totalSumOfCny = BigDecimalUtils.add(totalOfCny, totalSumOfCny);
            li.add(data);
        }
        accountMap.put("accountType", accountType);
        accountMap.put("list", li);
        accountMap.put("totalSumOfCny", BigDecimalUtils.toStringInZERO(totalSumOfCny, 2));
        return Result.toResult(ResultCode.SUCCESS, accountMap);
    }

    @Override
    public String queryCoinAvailBalance(User user, Integer coinType, Integer accountType) {
        Map<String, Object> accountMap = new HashMap();

        Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(),coinType,accountType);
        accountMap.put("availBalance",BigDecimalUtils.toString(account==null?null:account.getAvailbalance()));
        accountMap.put("exchangeRate",baseService.getPriceOfCNY(coinType));
        return Result.toResult(ResultCode.SUCCESS,accountMap);
    }

    @Override
    public String withDrawApply(User user, String password, BigDecimal amount, Integer accountType, String onlineNum, Integer coinType) {
        CoinScale coinScale = coinScaleService.queryByCoin(coinType, CoinType.NONE);
        amount = BigDecimalUtils.roundDown(amount, coinScale.getWithdrawScale());
        Map<String, Object> map = new HashMap<String, Object>();
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
		/*功能开关*/
        try {
            if(accountType == GlobalParams.ACCOUNT_TYPE_SPOT && coinManage.getWithspotonoff() != 1){
                return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
            }
            if(accountType == GlobalParams.ACCOUNT_TYPE_C2C && coinManage.getWithc2conoff() != 1){
                return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
            }
        } catch (IllegalArgumentException e) {
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }

		/*输入金额校验*/
        BigDecimal minAmt = amountEnable(coinManage, amount);
        if (minAmt != null) {
            return Result.toResultFormat(ResultCode.WITHDRAW_AMOUNT_MIN_LIMIT, minAmt.toPlainString());
        }

		/*实名认证判断*/
        if (user.getIdstatus() == 0) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

        Map<String, Object> dayLimit = withdrawService.queryDayWithdraw(user.getId(), DATE.getCurrentDateStr(), coinType);
        //日提现次数限制
        int times = ((Long) dayLimit.get("amountCount")).intValue();
        int withdrawCountMax = coinManage.getWithdrawcountmax();
        if (times > withdrawCountMax) {
            return Result.toResult(ResultCode.WITHDRAW_COUNT_LIMINT);
        }

        //日提现总金额限制
        BigDecimal sum = BigDecimalUtils.add(new BigDecimal(dayLimit.get("amountSum").toString()), amount);
        BigDecimal withAmountMax = coinManage.getWithamountmax();
        if (withAmountMax.compareTo(sum) == -1) {
            return Result.toResult(ResultCode.WITHDRAW_SUM_LIMIT);
        }

        Account account = null;
        BigDecimal availBalance = new BigDecimal(0);
		/*余额验证*/
        account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, accountType);
        if (account != null) {
            availBalance = account.getAvailbalance();
        }


        if (amount.compareTo(availBalance) == 1) {
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH, map);
        }
        //保存提现订单
        Withdraw withdraw = createWithDraw(user.getId(), onlineNum, accountType, GlobalParams.PAY_COMMON, coinType, amount);

		/*更新账户并记录流水*/
        accountService.updateAccountAndInsertFlow(user.getId(), accountType, coinType, BigDecimalUtils.plusMinus(amount), BigDecimal.ZERO, user.getId(), "提现申请", withdraw.getId());


        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String withDrawQueryAll(User user, Integer page, Integer rows, Integer accountType, Integer coinType) throws ParseException {
        Map<String, Object> data = new HashMap<String, Object>();
        Integer pageInt = page == null ? 0 : page;
        Integer rowsInt = rows == null ? 10 : rows;

        List<Withdraw> list = withdrawService.queryByUserIdAndType(user.getId(), pageInt, rowsInt, accountType, coinType);
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<WithdrawModel> l = new LinkedList<>();
        for (Withdraw withdraw : list) {
            Integer coinCalcul = coinScaleService.queryByCoin(coinType, -1).getCalculscale();
            WithdrawModel wd = new WithdrawModel();
            wd.setId(withdraw.getId());
            wd.setCreatetime(time.format(withdraw.getCreatetime()));
            wd.setType(withdraw.getType());
            wd.setRemark(withdraw.getRemark());
            wd.setPayaddress(withdraw.getPayaddress());
            wd.setRemain(BigDecimalUtils.toStringInZERO(withdraw.getRemain(), coinCalcul));
            wd.setAccounttype(withdraw.getAccounttype());
            wd.setAmount(BigDecimalUtils.toStringInZERO(withdraw.getAmount(), coinCalcul));
            wd.setFee(BigDecimalUtils.toStringInZERO(withdraw.getFee(),coinCalcul));
            wd.setState(withdraw.getState());
            wd.setOrdernum(withdraw.getOrdernum());
            wd.setUpdatetime(time.format(withdraw.getUpdatetime()));
            l.add(wd);
            /*Map<String, Object> map = new HashMap<>();
            BigDecimal amount = withdraw.getAmount();
            map.put("amount", BigDecimalUtils.toStringInZERO(amount, coinScaleService.queryByCoin(coinType, -1).getCalculscale()));
            withdraw.setcTime(time.format(withdraw.getCreatetime()));
            map.put("createTime", time.format(withdraw.getCreatetime()));
            withdraw.setuTime(time.format(withdraw.getUpdatetime()));
            map.put("updateTime", TimeStampUtils.toDateString(withdraw.getUpdatetime()));*/
        }
        data.put("list", l);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String accountDetails(User user,Integer coinType,  Integer accountType, Integer page, Integer rows) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        page = page == null ? 0 : page;
        rows = rows == null ? 10 : rows;
        Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, accountType);
        CoinScale coinScale = coinScaleService.queryByCoin(coinType, -1);
        CoinManage coin = coinManageService.queryByCoinType(coinType);
        if (account == null) {
            map.put("availBalance", BigDecimalUtils.toStringInZERO(new BigDecimal(0), coinScale.getCalculscale()));
            map.put("frozenBlance", BigDecimalUtils.toStringInZERO(new BigDecimal(0), coinScale.getCalculscale()));
            map.put("availBalanceCny", BigDecimalUtils.toStringInZERO(new BigDecimal(0), coinScale.getCalculscale()));
            map.put("frozenBlanceCny", BigDecimalUtils.toStringInZERO(new BigDecimal(0), coinScale.getCalculscale()));
        } else {
            String availBalance = BigDecimalUtils.toStringInZERO(account.getAvailbalance(), coinScale.getCalculscale());
            String frozenBlance = BigDecimalUtils.toStringInZERO(account.getFrozenblance(), coinScale.getCalculscale());
            map.put("availBalance", availBalance);
            map.put("frozenBlance", frozenBlance);
            BigDecimal exchangeRate = getPriceOfCNY(coinType);
            map.put("availBalanceCny", BigDecimalUtils.toStringInZERO(exchangeRate.multiply(new BigDecimal(Double.valueOf(availBalance))), coinScale.getCalculscale()));
            map.put("frozenBlanceCny", BigDecimalUtils.toStringInZERO(exchangeRate.multiply(new BigDecimal(Double.valueOf(frozenBlance))), coinScale.getCalculscale()));
//            map.put("exchangeRate", getPriceOfCNY(coinType).doubleValue());
//            map.put("availOfcnyScale", coinScale.getAvailofcnyscale());
        }
        map.put("cnName", coin.getCnname());
        List<Flow> list = flowService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, accountType, page, rows);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        for (Flow flow : list) {
            flow.setTime(sdf.format(flow.getCreatetime()));
            flow.setResultAmount(BigDecimalUtils.toStringInZERO(flow.getAmount(),coinScaleService.queryByCoin(coinType, -1).getCalculscale()));
        }
        map.put("FlowList", list);
        return Result.toResult(ResultCode.SUCCESS, map);
    }
    @Override
    public String rechargeInfo(User user, Integer coinType) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("rechSpotOnoff", GlobalParams.ACTIVE);
        params.put("cointype", coinType);
        List<CoinManage> list = coinManageService.selectAll(params);
        if (list == null || list.isEmpty() || list.size() == 0) {
            return Result.toResult(ResultCode.RECHARGE_RECH_SPOT_OFF);
        }
        CoinManage coinManage = list.get(0);
        String secretKey = "";
        Byte b = 0;
        String createUrl = "";
        String rechImgUrl = "";
        String rechAddress = "";
        createUrl = coinManage.getGetAddress();
        Map<Object, Object> chainParam = new HashMap<Object, Object>();
        chainParam.put("userid", user.getId());
        chainParam.put("type", coinType);
        List<AccountChain> accountChainList = accountChainService.selectAll(chainParam);
        if (accountChainList.size() == 0 || accountChainList.isEmpty()) {
            if (createUrl == null || createUrl == "" || "".equals(createUrl)) {
                log.info("【创建钱包】钱包创建失败，暂未设置生成URL接口。币种编码【" + coinType + "】");
                return Result.toResult(ResultCode.RECHARGE_RECH_FLASE);
            }
            if (coinType == 4 || coinType == 8) {
                rechAddress = walletUtil.createETHAddress(createUrl, coinType);
                /* 验证地址是否错误 */
                if (StrUtils.isBlank(rechAddress) || rechAddress.contains("<")) {
                    log.info("【创建钱包】【" + user.getPhone() + "】币种编码【" + coinType + "】钱包服务器发生异常错误");
                    return Result.toResult(ResultCode.RECHARGE_RECH_FLASE);
                }
                Gson gson = new Gson();
                ApiResult api = gson.fromJson(rechAddress, ApiResult.class);
                secretKey = api.getTime();
                rechAddress = api.getAddress();

            } else {
                rechAddress = walletUtil.createAddress(createUrl);
                    /* 验证地址是否错误 */
                if (StrUtils.isBlank(rechAddress) || rechAddress.contains("<") || rechAddress == null) {
                    log.info("【创建钱包】【" + user.getPhone() + "】币种编码【" + coinType + "】钱包服务器发生异常错误");
                    return Result.toResult(ResultCode.RECHARGE_RECH_FLASE);
                }
                secretKey = "";
            }

				/* 将地址存入数据库 */
            AccountChain ac = new AccountChain();
            ac.setAddress(rechAddress);
            ac.setUserid(user.getId());
            ac.setType(coinType);
            ac.setSecretkey(secretKey);
            ac.setSize(0);
            accountChainService.insert(ac);
            log.info("【创建钱包】【" + user.getPhone() + "】币种编码【" + coinType + "】地址为【" + rechAddress + "】");
        } else {
            rechAddress = accountChainList.get(0).getAddress();
        }
        RechargeInfo ri = new RechargeInfo();
        ri.setCnName(coinManage.getCnname());
        ri.setCoinName(coinManage.getCoinname());
        ri.setRechAddress(rechAddress);
        ri.setRechargeInfo(coinManage.getRechargeinfo());
        return Result.toResult(ResultCode.SUCCESS, ri);
    }
    @Override
    public String transfer(User user, Integer type, Integer coinType, BigDecimal amount, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!TransferEnable(type, coinType)) {
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

		/*实名认证判断*/
        if (user.getIdstatus() == 0) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }


        int fromType = type == GlobalParams.ACCOUNT_TRANSFER_TYPE_C2CTOSPOT ? GlobalParams.ACCOUNT_TYPE_C2C : GlobalParams.ACCOUNT_TYPE_SPOT;
        int toType = type == GlobalParams.ACCOUNT_TRANSFER_TYPE_C2CTOSPOT ? GlobalParams.ACCOUNT_TYPE_SPOT : GlobalParams.ACCOUNT_TYPE_C2C;
		/*余额验证*/
        Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, fromType);
        if (account == null || account.getAvailbalance().compareTo(amount) == -1) {
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }
		/*保存划转记录*/
        AccountTransfer trans = new AccountTransfer();
        trans.setFromaccount(fromType);
        trans.setToaccount(toType);
        trans.setUserid(user.getId());
        trans.setCointype(coinType);
        trans.setAmount(amount);
        trans.setRelatedid(0);
        accountTransferService.insert(trans);

        try {
            accountService.updateAccountAndInsertFlow(user.getId(), fromType, coinType, BigDecimalUtils.plusMinus(amount), BigDecimal.ZERO, user.getId(), "资金划转-转出", trans.getId());
            accountService.updateAccountAndInsertFlow(user.getId(), toType, coinType, amount, BigDecimal.ZERO, user.getId(), "资金划转-转入", trans.getId());
        } catch (Exception e) {
            log.info("资金划转失败");
            e.printStackTrace();
            throw new RuntimeException();
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String withDrawInfo(User user, Integer coinType, Integer accountType) {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> params = new HashMap<>();
        params.put("userid", user.getId());
        params.put("cointype", coinType);
        params.put("accounttype", accountType);
        List<Account> list = accountService.selectAll(params);
        CoinManage cm = coinManageService.queryByCoinType(coinType);
        data.put("fee", BigDecimalUtils.toStringInZERO(cm.getWithspotrate(), coinScaleService.queryByCoin(coinType, -1).getCalculscale()));
        if (list == null || list.isEmpty() || list.size() == 0) {
            data.put("availBalance", "0");
        } else {
            data.put("availBalance", BigDecimalUtils.toStringInZERO(list.get(0).getAvailbalance(), coinScaleService.queryByCoin(coinType, -1).getCalculscale()));
        }
        data.put("info", cm.getWithdrawinfo());
        return Result.toResult(ResultCode.SUCCESS, data);
    }


    /**
     * 资金划转开关
     *
     * @param type
     * @param coinType
     * @return boolean
     * @date 2018-3-27
     * @author lina
     */
    public boolean TransferEnable(Integer type, Integer coinType) {
        CoinManage mag = coinManageService.queryByCoinType(coinType);
        Integer val = type == GlobalParams.ACCOUNT_TRANSFER_TYPE_C2CTOSPOT ? mag.getC2ctospotonoff() : mag.getSpottoc2conoff();
        String onoff = "";
        if(val == 1){
            onoff = mag.getC2ctospotonoff().toString();
        }else{
            onoff = mag.getSpottoc2conoff().toString();
        }
        return onoff.equals(GlobalParams.ACTIVE + "");
    }

    public Withdraw createWithDraw(Integer userId, String onlineNum, Integer accountType, Integer type, Integer coinType, BigDecimal amount) {
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
		/*手续费计算 提现到现货没有费率，其他有费率*/
        BigDecimal fee = type == GlobalParams.PAY_SPOT ? BigDecimal.ZERO : coinManage.getWithspotrate();
        BigDecimal remain = type == GlobalParams.PAY_SPOT ? amount : BigDecimalUtils.subtract(amount, fee, coinScaleService.queryByCoin(coinType, -1).getCalculscale());

        Withdraw withdraw = new Withdraw();
        withdraw.setUserid(userId);
        withdraw.setPayaddress(onlineNum == null ? "" : onlineNum);
        withdraw.setType(type);
        withdraw.setAccounttype(accountType);
        //TODO 付款账户
        withdraw.setAmount(amount);
        withdraw.setFee(fee);
        withdraw.setRemain(remain);
        withdraw.setCointype(coinType);
        withdraw.setState(type == GlobalParams.PAY_SPOT ? GlobalParams.ORDER_STATE_TREATED : GlobalParams.ORDER_STATE_UNTREATED);
        withdraw.setOrdernum("W" + userId + System.currentTimeMillis());
        withdraw.setRemark("");
        withdrawService.insert(withdraw);
        return withdraw;
    }

    /**
     * 获取折合人民币的价格
     *
     * @param coinType
     * @return BigDecimal
     * @date 2018-2-10
     * @author lina
     */
    public BigDecimal getPriceOfCNY(Integer coinType) {
        if (coinType == CoinType.KN) {
            return BigDecimal.ONE;
        }
        BigDecimal c2cPrice = getC2CLatestPrice(coinType);
        if (c2cPrice.compareTo(BigDecimal.ZERO) == 1) {
            return c2cPrice;
        }
        return getSpotLatestPrice(coinType, CoinType.KN);
    }

    /**
     * 获取现货最新的成交价
     *
     * @param orderCoinType
     * @param unitCoinType
     * @return BigDecimal
     * @date 2018-2-10
     * @author lina
     */
    public BigDecimal getSpotLatestPrice(Integer orderCoinType, Integer unitCoinType) {
        String key = String.format(RedisKey.LATEST_TRANS_PRICE, unitCoinType, orderCoinType);
        String price = RedisUtil.searchString(redis, key);
        return StrUtils.isBlank(price) ? new BigDecimal(0) : new BigDecimal(price);
    }

    /**
     * 获取C2C最新的成交价
     *
     * @param coinType
     * @return BigDecimal
     * @date 2018-2-10
     * @author lina
     */
    public BigDecimal getC2CLatestPrice(Integer coinType) {
        String key = String.format(RedisKey.C2C_PRICE, coinType);
        String price = RedisUtil.searchString(redis, key);
        return StrUtils.isBlank(price) ? new BigDecimal(0) : new BigDecimal(price);
    }

    /**
     * 判断输入金额是否大于最低提现金额,true返回null，false返回最低提现金额
     * @param amount
     * @return BigDecimal
     * @date 2018-3-13
     * @author lina
     */
    public BigDecimal amountEnable(CoinManage coinManage, BigDecimal amount){

        String minVal = coinManage.getWithamountmin().toString();
        BigDecimal minAmt = new BigDecimal(minVal);
         if(amount.compareTo(minAmt)==-1){
            return minAmt;
        }
        return null;
    }

    /**
     * 给现有用户增加钱包
     */
    @Override
    public void addWallet(){
        Map<Object, Object> map = new HashMap<>();
        Integer page = 0;
        map.put("firstResult", page);
        map.put("maxResult", 100);
        List<User> list = userService.selectPaging(map);
        List<Integer> coinType = new LinkedList<>();
        List<Integer> accountType = new LinkedList<>();
        coinType.add(0);
        coinType.add(1);
        coinType.add(2);
        coinType.add(3);
        coinType.add(4);
        coinType.add(5);
        coinType.add(8);
        coinType.add(11);
        coinType.add(12);
        coinType.add(13);
        coinType.add(14);
        accountType.add(0);
        accountType.add(1);
        accountType.add(4);
        while (list != null && !list.isEmpty()){
            for(Integer accType : accountType) {
                for (Integer type : coinType) {
                    for (User user : list) {
                        Account account = accountService.getAccountByUserAndCoinTypeAndAccount(user.getId(), type, accType);
                        if(account == null){
                            Account acc = new Account();
                            acc.setUserid(user.getId());
                            acc.setCointype(type);
                            acc.setAvailbalance(new BigDecimal(0));
                            acc.setFrozenblance(new BigDecimal(0));
                            acc.setAccounttype(accType);
                            accountService.insert(acc);
                        }
                    }
                }
            }
            page++;
            map.put("firstResult", page);
            list = userService.selectPaging(map);
        }
    }
}
