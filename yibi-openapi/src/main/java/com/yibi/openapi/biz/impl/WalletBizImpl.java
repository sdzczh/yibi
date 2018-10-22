package com.yibi.openapi.biz.impl;

import com.yibi.common.encrypt.MD5;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.openapi.biz.WalletBiz;
import com.yibi.openapi.commons.enums.ResultCode;
import com.yibi.openapi.commons.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Log4j2
public class WalletBizImpl implements WalletBiz {
    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate<String, String> redis;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FlowService flowService;

    @Override
    public Object withdrawMoney(String account, String password, int type, int accountType, Integer coinType, BigDecimal money) {
        //获取提现用户
        User user = userService.selectByPhone(account);
        if(user == null){
            return Result.toResultNotEncrypt(ResultCode.USER_NOT_EXIST);
        }

        /*是否设置交易密码*/
        if(StrUtils.isBlank(user.getOrderpwd())){
            return Result.toResultNotEncrypt(ResultCode.ORDERPWD_NOT_EXISITED);
        }

        /*实名认证判断*/
        if(user.getIdstatus()==0){
            return  Result.toResultNotEncrypt(ResultCode.USER_NOT_REALNAME);
        }

        /*交易密码验证*/
        String resu = validateOrderPassword(user, password);
        if(!StrUtils.isBlank(resu)){
            return resu;
        }

        BigDecimal amount = money;

        Withdraw withdraw = createWithDraw(user.getId(),GlobalParams.ACCOUNT_TYPE_GAME,type, coinType, amount);

        try {

            /*增加现货钱包余额，并记录流水*/
            if(type == GlobalParams.PAY_SPOT){
                Map<Object, Object> selectSpot = new HashMap<Object, Object>();
                selectSpot.put("userid", user.getId());
                selectSpot.put("cointype", coinType);
                selectSpot.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
                List<Account> list = accountService.selectAll(selectSpot);
                Account spot = null;
                if(list != null && !list.isEmpty()){
                    spot = list.get(0);
                }
                if(spot == null){
                    spot = new Account();
                    spot.setUserid(user.getId());
                    spot.setCointype(coinType);
                    spot.setAvailbalance(amount);
                    spot.setFrozenblance(BigDecimal.ZERO);
                    spot.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                    accountService.insertSelective(spot);
                }else{
                    spot.setAvailbalance(spot.getAvailbalance().add(amount));
                    accountService.updateByPrimaryKeySelective(spot);
                }
                Flow flow = new Flow();
                flow.setUserid(user.getId());
                flow.setCointype(coinType);
                flow.setOpertype("dkbaby提现");
                flow.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                flow.setRelateid(withdraw.getId());
                flow.setOperid(user.getId());
                flow.setAccamount(spot.getAvailbalance());
                flow.setAmount(amount);
                flowService.insertSelective(flow);
            }
        } catch (Exception e) {
            log.info("提现失败");
            e.printStackTrace();
            throw new RuntimeException();
        }
        //查询现货账户DK币余额
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("userid", user.getId());
        params.put("cointype", coinType);
        params.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
        List<Account> list = accountService.selectAll(params);
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(list == null || list.isEmpty()){
            retMap.put("DKAmount", new BigDecimal(0).doubleValue());
        }
        retMap.put("DKAmount", list.get(0).getAvailbalance().doubleValue());
        return Result.toResultNotEncrypt(ResultCode.SUCCESS, retMap);

    }

    @Override
    public Object rechargeGame(String account, String password, int type, int accountType, Integer coinType, BigDecimal money) {
        //获取充值用户
        User user = null;
        Map<Object, Object> param = new HashMap<Object, Object>();
        param.put("phone", account);
        List<User> listUser = userService.selectAll(param);
        if (listUser != null && !listUser.isEmpty()) {
            user = listUser.get(0);
        }
        if(user == null){
            return Result.toResultNotEncrypt(ResultCode.USER_NOT_EXIST);
        }

        /*是否设置交易密码*/
        if(StrUtils.isBlank(user.getOrderpwd())){
            return Result.toResultNotEncrypt(ResultCode.ORDERPWD_NOT_EXISITED);
        }

        /*实名认证判断*/
        if(user.getIdstatus()==0){
            return  Result.toResultNotEncrypt(ResultCode.USER_NOT_REALNAME);
        }

        /*交易密码验证*/
        String resu = validateOrderPassword(user, password);
        if(!StrUtils.isBlank(resu)){
            return resu;
        }

        BigDecimal amount = money;

        try {

            /*减少现货钱包余额，并记录流水*/
            if(type == GlobalParams.ACCOUNT_TYPE_SPOT){
                Map<Object, Object> selectSpot = new HashMap<Object, Object>();
                selectSpot.put("userid", user.getId());
                selectSpot.put("cointype", coinType);
                selectSpot.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
                List<Account> list = accountService.selectAll(selectSpot);
                Account spot = null;
                if(list != null && !list.isEmpty()){
                    spot = list.get(0);
                }
                if(spot == null){
                    spot = new Account();
                    spot.setUserid(user.getId());
                    spot.setCointype(coinType);
                    spot.setAvailbalance(amount);
                    spot.setFrozenblance(BigDecimal.ZERO);
                    spot.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                    accountService.insertSelective(spot);
                    return Result.toResultNotEncrypt(ResultCode.AMOUNT_NOT_ENOUGH);
                }else{
                    if(spot.getAvailbalance().compareTo(amount) < 0){
                        return Result.toResultNotEncrypt(ResultCode.AMOUNT_NOT_ENOUGH);
                    }
                    spot.setAvailbalance(spot.getAvailbalance().subtract(amount));
                    accountService.updateByPrimaryKeySelective(spot);
                }
                Flow flow = new Flow();
                flow.setUserid(user.getId());
                flow.setCointype(coinType);
                flow.setOpertype("充值dkbaby");
                flow.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                flow.setOperid(user.getId());
                flow.setAccamount(spot.getAvailbalance());
                flow.setAmount(BigDecimalUtils.plusMinus(amount));
                flowService.insertSelective(flow);
            }
        } catch (Exception e) {
            log.info("充值失败");
            e.printStackTrace();
            throw new RuntimeException();
        }
        //查询现货账户DK币余额
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("userid", user.getId());
        params.put("cointype", coinType);
        params.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
        List<Account> list = accountService.selectAll(params);
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(list == null || list.isEmpty()){
            retMap.put("DKAmount", new BigDecimal(0).doubleValue());
        }
        retMap.put("DKAmount", list.get(0).getAvailbalance().doubleValue());
        return Result.toResultNotEncrypt(ResultCode.SUCCESS, retMap);
    }

    @Override
    public Object queryAvailBalance(String phone, Integer coinType) {
        User user = userService.selectByPhone(phone);
        if(user == null){
            return Result.toResultNotEncrypt(ResultCode.USER_NOT_EXIST);
        }
        Integer userid = user.getId();
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("userid", userid);
        params.put("cointype", coinType);
        params.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
        List<Account> list = accountService.selectAll(params);
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(list == null || list.isEmpty()){
            retMap.put("DKAmount", new BigDecimal(0).doubleValue());
        }
        retMap.put("DKAmount", list.get(0).getAvailbalance().doubleValue());
        return Result.toResultNotEncrypt(ResultCode.SUCCESS, retMap);
    }

    public String validateOrderPassword(User user, String password) {
        /*是否设置交易密码*/
        if(StrUtils.isBlank(user.getOrderpwd())){
            return Result.toResultNotEncrypt(ResultCode.ORDERPWD_NOT_EXISITED);
        }

        /*密码锁定校验*/
        Map<String, Object> map = new HashMap<String, Object>();
        if(orderPwdLock(user ,map)){
            return Result.toResultFormatNotEncrypt(ResultCode.ORDERPWD_IN_LOCK, map.get("timeRemain"));
        }

        /*验证交易密码*/
        int result = 1;
        try {
            result = orderPwdCheck(user.getId(),user.getOrderpwd(),password,map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        //交易密码错误
        if(result == 0){
            return  Result.toResultFormatNotEncrypt(ResultCode.ORDERPWD_ERROR, map.get("errorTimesRemain"));
        }else if(result ==-1){
            return  Result.toResultFormatNotEncrypt(ResultCode.ORDERPWD_IN_LOCK, map.get("timeRemain"));
        }

        return null;
    }

    /**
     * 交易密码锁定校验
     * @param user 用户
     * @param map 需要返回的map
     * @return boolean
     * @date 2018-1-9
     * @author lina
     */
    public boolean orderPwdLock(User user, Map<String, Object> map) {
        String lockTimesKey = String.format(RedisKey.ORDER_PASSWORD_LOCK_TIMESTAMP, user.getId());
        String lockTime = RedisUtil.searchString(redis, lockTimesKey);
        if(!StrUtils.isBlank(lockTime)){
            int lockTimeLimit = 10;
            Sysparams param = sysparamsService.getValByKey(SystemParams.ORDERPWD_LOCK_INTERVAL);
            if(param!=null){
                lockTimeLimit = Integer.parseInt(param.getKeyval());
            }
            //正在锁定期中,返回剩余锁定时间
            long currentTime = System.currentTimeMillis();
            int interval = (int)Math.ceil(currentTime-Long.parseLong(lockTime))/60000;
            map.put("timeRemain", lockTimeLimit-interval);
            return true;
        }else{
            return false;
        }

    }


    /**
     * 交易密码验证
     * @param userId
     * @param userPwd
     * @param pwd
     * @param map
     * @throws Exception
     * @return int -1:已锁定  0：交易密码错误  1：正确
     * @date 2018-1-26
     * @author lina
     */
    public int orderPwdCheck(Integer userId,String userPwd, String pwd, Map<String, Object> map)
            throws Exception {
        String pwdEncode = MD5.getMd5(pwd);

        if(!pwdEncode.equals(userPwd)){
            int errorTimeLimit = 0;
            int errorTimeCount = 0;
            int lockTime = 0;
            long currentTime = System.currentTimeMillis();
            Sysparams param = sysparamsService.getValByKey(SystemParams.ORDERPWD_ERROR_INTERVAL);
            Sysparams param1 = sysparamsService.getValByKey(SystemParams.ORDERPWD_ERROR_TIMES);
            Sysparams param2 = sysparamsService.getValByKey(SystemParams.ORDERPWD_LOCK_INTERVAL);
            if(param==null||param1==null||param2==null){
                map.put("errorTimesRemain",4);
                return 0;
            }else{
                errorTimeLimit = Integer.parseInt(param.getKeyval());
                errorTimeCount = Integer.parseInt(param1.getKeyval());
                lockTime = Integer.parseInt(param2.getKeyval());
            }
            String errorTimestampKey = String.format(RedisKey.ORDER_PASSWORD_ERROR_TIMESTAMP, userId);
            String errorTimesKey = String.format(RedisKey.ORDER_PASSWORD_ERROR_TIMES,userId);
            String errorTimestamp = RedisUtil.searchString(redis, errorTimestampKey);
            if(StrUtils.isBlank(errorTimestamp)){
                //如果交易密码第一次错误 ，则重置密码错误次数和时间
                RedisUtil.addString(redis, errorTimestampKey, errorTimeLimit*60, currentTime+"");
                RedisUtil.addString(redis, errorTimesKey , "1");
                map.put("errorTimesRemain", errorTimeCount-1);
                return 0;
            }else{
                /*如果距离上次密码错误时间在10分钟内，错误次数加1*/
                int times = getNextErrorTimes(errorTimesKey);
                if(times<errorTimeCount){
                    RedisUtil.addString(redis, errorTimesKey ,times+"" );
                    map.put("errorTimesRemain", errorTimeCount-times);
                    return 0;
                }else{
                    //如果密码错误次数已经达到5次，则锁定
                    String lockTimesKey = String.format(RedisKey.ORDER_PASSWORD_LOCK_TIMESTAMP, userId);
                    RedisUtil.addString(redis, lockTimesKey ,lockTime*60, currentTime+"");
                    map.put("timeRemain", lockTime);
                    return -1;
                }
            }
        }
        return 1;
    }


    /**
     * 从redis中获取错误次数并加1返回
     * @param key
     * @return int
     * @date 2018-1-26
     * @author lina
     */
    private int getNextErrorTimes(String key){
        String val = RedisUtil.searchString(redis, key);
        if(StrUtils.isBlank(val)){
            return 1;
        }
        return Integer.parseInt(val)+1;
    }

    public Withdraw createWithDraw(Integer userId,Integer accountType,Integer type,Integer coinType,BigDecimal amount){
        /*手续费计算 提现到现货没有费率，其他有费率*/
        BigDecimal fee = BigDecimal.ZERO;
        BigDecimal remain = amount;

        Withdraw withdraw = new Withdraw();
        withdraw.setUserid(userId);
        withdraw.setType(type);
        withdraw.setAccounttype(accountType);
        //TODO 付款账户
        withdraw.setPayaddress("");
        withdraw.setAmount(amount);
        withdraw.setFee(fee);
        withdraw.setRemain(remain);
        withdraw.setCointype(coinType);
        withdraw.setState( GlobalParams.ORDER_STATE_TREATED);
        withdraw.setOrdernum("W"+userId+System.currentTimeMillis());
        withdrawService.insertSelective(withdraw);
        return withdraw;
    }
}
