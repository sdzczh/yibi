package com.yibi.core.service;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/13 0013.
 */
public interface BaseService {

    /**
     * 获取现货交易最新成交价
     * @param orderCoinType
     * @param unitCoinType
     * @return
     */
    BigDecimal getSpotLatestPrice(Integer orderCoinType, Integer unitCoinType);

    /**
     * 外部获取usdt的价格
     * @return
     */
    BigDecimal getUsdtPrice();

    /**
     * 获取C2C交易最新成交价
     * @param coinType
     * @return
     */
    BigDecimal getC2CLatestPrice(Integer coinType);

    /**
     * 获取汇率
     * @param coinType
     * @return
     */
    BigDecimal getPriceOfCNY(Integer coinType);

}
