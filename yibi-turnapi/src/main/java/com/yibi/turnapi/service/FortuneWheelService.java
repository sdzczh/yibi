package com.yibi.turnapi.service;

import com.yibi.core.entity.User;

import java.math.BigDecimal;

public interface FortuneWheelService {
    /**
     * 扣除用户账户虚拟币
     * @param user
     * @param amount
     * @param coinType
     * @return
     */
    public Object deductCoin(User user, BigDecimal amount, Integer coinType);

    /**
     * 转盘奖励
     * @param user
     * @param type
     * @param coinType
     * @param amount
     * @return
     */
    public Object reward(User user, Integer type, Integer coinType, BigDecimal amount);
}
