package com.yibi.turnapi.service.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.entity.*;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.DigcalRecordService;
import com.yibi.core.service.FlowService;
import com.yibi.core.service.UserDiginfoService;
import com.yibi.turnapi.commons.enums.CalculForceType;
import com.yibi.turnapi.commons.enums.ResultCode;
import com.yibi.turnapi.commons.utils.Result;
import com.yibi.turnapi.commons.variables.GlobalParams;
import com.yibi.turnapi.service.FortuneWheelService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Service
@Transactional
public class FortuneWheelServiceImpl implements FortuneWheelService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private UserDiginfoService userDiginfoService;
    @Autowired
    private DigcalRecordService digcalRecordService;

    @Override
    public Object deductCoin(User user, BigDecimal amount, Integer coinType) {
        if (user == null) {
            return Result.toResultNotEncrypt(ResultCode.USER_NOT_EXIST);
        }
        /*实名认证判断*/
        if (user.getIdstatus() == 0) {
            return Result.toResultNotEncrypt(ResultCode.USER_NOT_REALNAME);
        }
        try {
            /*减少现货钱包余额，并记录流水*/
            Map<Object, Object> selectSpot = new HashMap<Object, Object>();
            selectSpot.put("userid", user.getId());
            selectSpot.put("cointype", coinType);
            selectSpot.put("accounttype",GlobalParams.ACCOUNT_TYPE_SPOT);

            List<Account> list = accountService.selectAll(selectSpot);
            Account spot = null;
            if (list != null && !list.isEmpty()) {
                spot = list.get(0);
            }
            //减少现货钱包金额
            if (spot == null) {
                spot = new Account();
                spot.setUserid(user.getId());
                spot.setCointype(coinType);
                spot.setAvailbalance(BigDecimal.ZERO);
                spot.setFrozenblance(BigDecimal.ZERO);
                spot.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                accountService.insertSelective(spot);
                return Result.toResultNotEncrypt(ResultCode.AMOUNT_NOT_ENOUGH);
            } else {
                if (spot.getAvailbalance().compareTo(amount) < 0) {
                    return Result.toResultNotEncrypt(ResultCode.AMOUNT_NOT_ENOUGH);
                }
                spot.setAvailbalance(spot.getAvailbalance().subtract(amount));
                accountService.updateByPrimaryKeySelective(spot);
            }
            //记录流水
            Flow flow = new Flow();
            flow.setOpertype("大转盘扣除");
            flow.setUserid(user.getId());
            flow.setAccamount(spot.getAvailbalance());
            flow.setAmount(BigDecimalUtils.plusMinus(amount));
            flow.setOperid(user.getId());
            flow.setRelateid(0);
            flow.setCointype(CoinType.DK);
            flow.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
            flowService.insertSelective(flow);
        } catch (Exception e) {
            log.info("扣除失败");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return Result.toResultNotEncrypt(ResultCode.SUCCESS);
    }

    @Override
    public Object reward(User user, Integer type, Integer coinType, BigDecimal amount) {
        if (type == 1) {//奖励魂力
            Integer digCal = amount.intValue();
            Map<Object, Object> params = new HashMap<Object, Object>();
            params.put("userid", user.getId());
            List<UserDiginfo> listDiginfo = userDiginfoService.selectAll(params);
            UserDiginfo diginfo = null;
            if (listDiginfo == null || listDiginfo.isEmpty()) {
                //如果用户魂力记录不存在，新增
                diginfo = new UserDiginfo();
                diginfo.setUserid(user.getId());
                diginfo.setDigcalcul(0);
                diginfo.setDigflag(false);
                diginfo.setLogindays(0);
                diginfo.setTenrewardstate(false);
                diginfo.setMonthrewardstate(false);
                userDiginfoService.insertSelective(diginfo);
            } else {
                diginfo = listDiginfo.get(0);
            }
            diginfo.setDigcalcul(diginfo.getDigcalcul() + digCal);
            userDiginfoService.updateByPrimaryKeySelective(diginfo);
            //增加记录
            DigcalRecord rec = new DigcalRecord();
            rec.setUserid(user.getId());
            rec.setType(CalculForceType.FORTUNEWHEEL.getCode());
            rec.setName(CalculForceType.FORTUNEWHEEL.getName());
            rec.setDigcalcul(digCal);
            rec.setAllcalculforce(diginfo.getDigcalcul());
            digcalRecordService.insertSelective(rec);
        }
        if (type == 2){//奖励虚拟币
            try {
                /*增加现货钱包余额，并记录流水*/
                Map<Object, Object> selectSpot = new HashMap<Object, Object>();
                selectSpot.put("userid", user.getId());
                selectSpot.put("cointype", coinType);
                selectSpot.put("accounttype",GlobalParams.ACCOUNT_TYPE_SPOT);
                List<Account> list = accountService.selectAll(selectSpot);
                Account spot = null;
                if (list != null && !list.isEmpty()) {
                    spot = list.get(0);
                }
                //增加现货钱包金额
                if (spot == null) {
                    spot = new Account();
                    spot.setUserid(user.getId());
                    spot.setCointype(coinType);
                    spot.setAvailbalance(BigDecimal.ZERO);
                    spot.setFrozenblance(BigDecimal.ZERO);
                    spot.setAccounttype(GlobalParams.ACCOUNT_TYPE_SPOT);
                    accountService.insertSelective(spot);
                }
                accountService.updateAccountAndInsertFlow(user.getId(),GlobalParams.ACCOUNT_TYPE_SPOT,coinType,amount,BigDecimal.ZERO,
                        user.getId(),"大转盘奖励",0);

            } catch (Exception e) {
                log.info("添加虚拟币数量失败");
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return Result.toResultNotEncrypt(ResultCode.SUCCESS);
    }
}
