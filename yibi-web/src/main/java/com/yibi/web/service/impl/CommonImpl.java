package com.yibi.web.service.impl;

import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.Recharge;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.RechargeService;
import com.yibi.web.service.CommonI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonImpl implements CommonI {
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;

    @Override
    public String queryValue(Integer coinType, String key) {
        String keyName = String.format(RedisKey.C2C_MANAGE, coinType);

        String val = RedisUtil.searchHashString(redis, keyName, key);
        if(StrUtils.isBlank(val)){
            Object valData = getValByKey(key,coinType);
            val =valData== null?"":valData.toString();
        }
        return val;
    }

    @Override
    public Object getValByKey(String key, Integer coinType) {
        Map<Object,Object> map  = new HashMap<>();
        map.put("cointype",coinType);
        List<CoinManage> list = coinManageService.selectAll(map);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.get(0).getAutorechargeamt();
    }

    @Override
    public void rechargeAuto(Integer userId, Integer coinType, BigDecimal rechargeAmt) {
        //生成充值记录
        Recharge recharge = new Recharge();
        recharge.setUserid(userId);
        recharge.setCointype(coinType);
        recharge.setAmount(rechargeAmt);
        recharge.setRemain(rechargeAmt);
        recharge.setFee(BigDecimal.ZERO);
        recharge.setOrdernum("R"+userId+System.currentTimeMillis());
        recharge.setState(GlobalParams.ORDER_STATE_TREATED);//已支付
        recharge.setAddress("机器人自动充值");
        rechargeService.insertSelective(recharge);
        //更新钱包 现货 添加流水
        this.accountService.updateAccountAndInsertFlow(
                userId,GlobalParams.ACCOUNT_TYPE_SPOT,coinType,rechargeAmt,
                BigDecimal.ZERO,userId,"机器人自动充值",0
        );
    }

    @Override
    public Integer orderAmtAmountScale(Integer orderCoinType, Integer unitCoinType) {
        Map<Object,Object> map = new HashMap<>();
        map.put("ordercointype",orderCoinType);
        map.put("unitcointype",unitCoinType);
        List<CoinScale> list = coinScaleService.selectAll( map);
        if (list.size() == 0){
            return null;
        }
        return list.get(0).getOrderamtamountscale();
    }

    @Override
    public Integer orderAmtPriceScale(Integer orderCoinType, Integer unitCoinType) {
        Map<Object,Object> map = new HashMap<>();
        map.put("ordercointype",orderCoinType);
        map.put("unitcointype",unitCoinType);
        List<CoinScale> list = coinScaleService.selectAll( map);
        if (list.size() == 0){
            return null;
        }
        return list.get(0).getOrderamtpricescale();
    }
}
