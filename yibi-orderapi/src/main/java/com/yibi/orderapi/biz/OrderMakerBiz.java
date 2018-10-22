package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
public interface OrderMakerBiz {
    /**
     * 商户下订单
     * @param user
     * @param coinType
     * @param price
     * @param amount
     * @param orderType
     * @param totalMin
     * @param totalMax
     * @param payType
     * @param password
     * @return String
     * @date 2018-2-23
     * @author lina
     */
    String makerReleaseDeal(User user, Integer coinType,
                            BigDecimal price, BigDecimal amount, Integer orderType,
                            BigDecimal totalMin, BigDecimal totalMax, Integer payType, String password);

    /**
     * 查询所有委托单的列表
     * @param user
     * @param coinType
     * @param state
     * @param page
     * @param rows
     * @return String
     * @date 2018-2-23
     * @author lina
     */
    String queryOrderList(User user, Integer orderType, Integer coinType, Integer state, Integer page, Integer rows);

    /**
     * 查询可用余额和最新成交价
     * @param user
     * @param coinType
     * @return
     * @return String
     * @date 2018-2-27
     * @author lina
     */
    String queryavailBalanceAndPrice(User user, Integer coinType);

    /**
     * 撤销订单
     * @param user
     * @param orderId
     * @param password
     * @return
     * @return String
     * @date 2018-2-23
     * @author lina
     */
    String cancelOrder(User user, Integer orderId, String password);

    /**
     * 是否接单
     * @param user
     * @param orderId
     * @return String
     * @date 2018-2-23
     * @author lina
     */
    String receiptOrder(User user, Integer orderId);


    /**
     * 查询所有的商家列表
     * @param coinType
     * @param orderType
     * @param page
     * @param rows
     * @return
     * @return String
     * @date 2018-2-24
     * @author lina
     */
    String queryOrderList(Integer coinType, Integer orderType, Integer page, Integer rows);
}
