package com.yibi.web.service;

import java.math.BigDecimal;

public interface CommonI {

    String queryValue(Integer coinType, String key);

    Object getValByKey(String key, Integer coinType);

    void rechargeAuto(Integer userId, Integer coinType, BigDecimal rechargeAmt);

    Integer orderAmtAmountScale(Integer orderCoinType,Integer unitCoinType);

    Integer orderAmtPriceScale(Integer orderCoinType,Integer unitCoinType);
}
