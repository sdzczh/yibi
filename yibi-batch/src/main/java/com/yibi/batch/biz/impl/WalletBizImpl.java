package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yibi.batch.api.EthTokenAPI;
import com.yibi.batch.biz.WalletBiz;
import com.yibi.batch.util.walletUtil;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SmsTemplateCode;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
@Slf4j
@Service
@Transactional
public class WalletBizImpl implements WalletBiz {
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
    private SysparamsService sysparamsService;
    @Autowired
    private AccountChainService accountChainService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private TokenApiService tokenApiService;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private SMSCodeUtil smsCodeUtil;

    public static BigDecimal WEI = new BigDecimal("1000000000000000000");



    @Override
    public List<AccountChain> getChainList(Integer type, int page, int rows) {
        return accountChainService.getChainList(type, page, rows);
    }

    @Override
    public void startCheckAccount(Integer type, List<AccountChain> list) {
        List<Map<String, String>> trans = new ArrayList<Map<String, String>>();
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer oldSize = null;  //用户数据库中记录的交易数量
        Integer newSize = null;  //用户查询到的链上的交易数量
        String amount = "";      //金额
        Integer confirmations = null; //确认数
        CoinManage cm = coinManageService.queryByCoinType(type); //根据币种查询
        Recharge rechargeInfo = new Recharge();
        Gson go = new Gson();
        for (AccountChain accountChain : list) {
            User user = userService.selectByPrimaryKey(accountChain.getUserid());
            if (user == null) {
                log.info("【轮询充值】用户不存在");
                continue;
            }
            Recharge recharge = new Recharge();
            if(type == CoinType.ETH){
                try {
                    trans = walletUtil.getEthTrans(accountChain.getAddress());
                } catch (Exception e) {
                    log.info("获取区块链浏览器数据失败，币种：" + type);
                    e.printStackTrace();
                }
            }else{
                trans = walletUtil.getListTransactions(accountChain.getAddress(), type, cm);
            }
            String txid = "";
            String value = "";
            Object ob = null;
            if (trans == null || trans.size() == 0) {
                continue;
            }
             for (Map<String, String> map : trans) {
                 if(type == CoinType.ETH){
                    String to_address = map.get("to"); // 接收方地址
                    txid = map.get("hash");
                    value = map.get("value");
                    amount = new BigDecimal(value).divide(WEI).setScale(8, BigDecimal.ROUND_DOWN).toString();
                    confirmations = new Integer(map.get("confirmations"));
                    if (!to_address.equals(accountChain.getAddress())) { // 只处理充值方
                        continue;
                    }
                }else {
                    ob = map.get("amount");
                    value = ob.toString();
                    txid = map.get("txid");
                    amount = value;
                    confirmations = Integer.valueOf(String.valueOf(map.get("confirmations")));
                }
                // 充值
                //这里需要操作数据库，根据txid查询充值记录
                 Recharge rechargeByTxid = rechargeService.getRechargeByTxid(txid);
                 rechargeInfo = rechargeByTxid;
                if (rechargeInfo == null && confirmations > 6) {
                    //这里需要操作数据库，插入一条充值记录，并且增加address账户的资产
                        /*添加充值记录*/
                    recharge = rechargeService.rechargeApply(user, BigDecimal.valueOf(Double.valueOf(amount)), type, txid);
                    if (recharge == null) {
                        log.info("【轮询充值】【失败】【" + user.getPhone() + "】" + "=====币种编码【" + type + "】=====" + resultMap.get("msg"));
                        return;
                    }
                    try {
                        Account account = new Account();
                        /*查看对应币种的用户有无账户*/
                        Account acc = accountService.getSpotAcountByUserAndCoinType(user, type);
                        if(acc == null) {
                            accountService.addAccount(user, BigDecimal.valueOf(Double.valueOf(amount)), type, GlobalParams.ACCOUNT_TYPE_SPOT); //创建现货账户 初始化余额
                        }else{
                            acc.setAvailbalance(acc.getAvailbalance().add(new BigDecimal(amount)));
                            accountService.updateByPrimaryKeySelective(acc);
                        }
		                /*发送短信*/
                        Map<String, String> codeMap = new HashMap<String, String>();
                        smsCodeUtil.sendSms(user.getPhone(),SmsTemplateCode.SMS_RECHARGE_NOTIFY,codeMap);

                        /*保存流水*/
                        account = accountService.getSpotAcountByUserAndCoinType(user, type);
//                        BigDecimal accAmount = account.getAvailbalance();
//                        accAmount = accAmount.add(BigDecimal.valueOf(Double.valueOf(amount)));
                        Flow flow = new Flow();
                        flow.setCointype(type);
                        flow.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                        flow.setUserid(user.getId());
                        flow.setAmount(new BigDecimal(amount));
                        flow.setAccamount(account.getAvailbalance());
                        flow.setOperid(user.getId());
                        flow.setOpertype("充值");
                        flow.setRelateid(recharge.getId());
                        flowService.insertSelective(flow);
                        log.info("【轮询充值】【成功】=======" + user.getPhone() + "充值的" + type + ",数量为" + amount + "个=======");
                    } catch (Exception e) {
                        throw new RuntimeException("保存充值记录失败");
                    }

                } else {
                    continue;
                }
            }
        }

    }

    @Override
    public void transToMainAccount(Integer type, List<AccountChain> accountChainList) {
        String fromAddress = "";  //转出地址
        String value = "";        //转出金额
        String result = "";       //转账返回结果
        String secretKey = "";    //密钥
        String fee = "";          //矿工费
        BigDecimal amount;        //实际转出金额
        for (AccountChain accountChain : accountChainList) {
            User user = userService.selectByPrimaryKey(accountChain.getUserid());
            if (user == null) {
                log.info("【轮询充值】用户不存在");
                continue;
            }
            fromAddress = accountChain.getAddress();
            secretKey = accountChain.getSecretkey();
            if (StrUtils.isBlank(fromAddress) || StrUtils.isBlank(secretKey)) {
                continue;
            }
            try {
                value = walletUtil.getBalance(fromAddress, type, user);
                fee = walletUtil.getFee(type);
            } catch (Exception e) {
                log.info("【币种余额转移】【失败】【用户ID：" + accountChain.getUserid() + "】" + "=====币种编码【" + type + "】======【查询余额失败】");
                e.printStackTrace();
            }
            if (value == null || fee == null) {
                log.info("【币种余额转移】【失败】【用户ID：" + accountChain.getUserid() + "】" + "=====币种编码【" + type + "】======【查询余额失败】");
                continue;
            }

            amount = new BigDecimal(value).subtract(new BigDecimal(fee));
            result = walletUtil.transToMainAccount(fromAddress, amount, type, secretKey, user);
            if (result == null) {
                continue;
            }
        }
    }

    static Integer curBlockIndex = 6144500; //平台上线时的区块索引
    static Integer nodeBlockIndex = 0; //当前节点的最新区块索引
    @Override
    public void recargeERC(Integer type, List<AccountChain> accountChainList) {
        Map map = new HashMap();
        map.put("cointype", type);
        List<TokenApi> apiList = tokenApiService.selectAll(map);
        TokenApi api = apiList == null || apiList.isEmpty() ? null : apiList.get(0);
        EthTokenAPI tokenAPI = new EthTokenAPI(api.getRpcurl(), api.getRpcport(),  api.getTokenaddress(), api.getTransmethodid(), api.getBalancemethodid(), api.getWei());
        nodeBlockIndex = tokenAPI.eth_blockNumber().intValue();
        for(AccountChain accountChain : accountChainList) {
            User user = userService.selectByPrimaryKey(accountChain.getUserid());
            for (int blockIndex = curBlockIndex; blockIndex <= nodeBlockIndex; blockIndex++) {
//                System.out.println("..........开始处理区块【" + blockIndex + "】.............................................................................................................");
                //处理每个区块
                log.info("【YT充值】====当前区块【" + blockIndex + "】");
                String block = tokenAPI.eth_getBlockByNumber(new BigDecimal(blockIndex));
                if (!block.equals("")) {
                    Map blockMap = JSON.parseObject(block, Map.class);
                    List<Map> txList = JSON.parseObject(blockMap.get("transactions").toString(), List.class);
                    for (Map tx : txList) {
                        String tokenAddress = tokenAPI.getTokenAddress();
                        Object to = tx.get("to");
                        if (tokenAPI.getTokenAddress().equalsIgnoreCase(to == null ? "" : to.toString())) { //判断是否为对应代币
                            String input = tx.get("input").toString();
                            String from = tx.get("from").toString();
                            String[] resolveInput = tokenAPI.resolveInput(input);
                            String address = accountChain.getAddress();
                            String toAddress = resolveInput[0];
                            BigDecimal value = tokenAPI.unit16To10(resolveInput[1]).divide(tokenAPI.getWei());
                            //判断是否为此场合的地址
                            if(address.equals(toAddress)) {
                                /*添加充值记录*/
                                Object ObjectHash = tx.get("hash");
                                String hash = ObjectHash == null ? "" : ObjectHash.toString();
                                Recharge rechargeInfo = rechargeService.getRechargeByTxid(hash);
                                //单笔充值记录只充值一次
                                if (rechargeInfo == null) {
                                    Recharge recharge = rechargeService.rechargeApply(user, value, type, hash);
                                    if (recharge == null) {
                                        log.info("【轮询充值】【失败】【" + user.getPhone() + "】" + "=====币种编码【" + type + "】=====");
                                        return;
                                    }
                                    try {
                                        Account account = new Account();
                                    /*查看对应币种的用户有无账户*/
                                        Account acc = accountService.getSpotAcountByUserAndCoinType(user, type);
                                        if (acc == null) {
                                            accountService.addAccount(user, value, type, GlobalParams.ACCOUNT_TYPE_SPOT); //创建现货账户 初始化余额
                                        } else {
                                            acc.setAvailbalance(acc.getAvailbalance().add(value));
                                            accountService.updateByPrimaryKeySelective(acc);
                                        }
		                            /*发送短信*/
                                        Map<String, String> codeMap = new HashMap<String, String>();
                                        smsCodeUtil.sendSms(user.getPhone(), SmsTemplateCode.SMS_RECHARGE_NOTIFY, codeMap);

                                    /*保存流水*/
                                        account = accountService.getSpotAcountByUserAndCoinType(user, type);
                                        Flow flow = new Flow();
                                        flow.setCointype(type);
                                        flow.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                                        flow.setUserid(user.getId());
                                        flow.setAmount(value);
                                        flow.setAccamount(account.getAvailbalance());
                                        flow.setOperid(user.getId());
                                        flow.setOpertype("充值");
                                        flow.setRelateid(recharge.getId());
                                        flowService.insertSelective(flow);
                                        log.info("【轮询充值】【成功】=======" + user.getPhone() + "充值的" + type + ",数量为" + value + "个=======");
                                    } catch (Exception e) {
                                        throw new RuntimeException("保存充值记录失败");
                                    }
//                                    System.out.print("平台的地址充值成功+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                                }
                                log.info("【{}】成功充值【{}】,txid【{}】", toAddress, value);
                            }
                        }
                    }
                }

                curBlockIndex = blockIndex; //更新区块索引
            }
        }
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
        BigDecimal minAmt = new BigDecimal(0);
        String minVal = coinManage.getWithamountmin().toString();
         if(amount.compareTo(minAmt)==-1){
            return minAmt;
        }
        return null;
    }


}
