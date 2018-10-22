package com.yibi.orderapi.biz;

import com.yibi.core.entity.Recharge;
import com.yibi.core.entity.User;

import java.math.BigDecimal;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface RechargeBiz {
    /**
     * 添加充值信息
     * @param user
     * @param amount 金额
     * @param coinType 币种
     * @param txid hash
     * @return
     */
    Recharge rechargeApply(User user, BigDecimal amount, Integer coinType, String txid);
}
