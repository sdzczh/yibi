package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/16 0016.
 */
public interface OrderTakerBiz {

    /**
     * 普通用户买入
     * @param user
     * @param coinType
     * @param amount
     * @param orderId
     * @param password
     * @return
     * @return String
     * @date 2018-2-24
     * @author lina
     */
    String takerReleaseDeal(User user, Integer coinType,
                            BigDecimal amount, Integer orderId, String password, Integer orderType) ;


    /**
     * 查询订单列表
     * @param user
     * @param coinType 币种
     * @param orderType 交易类型 0买入 1卖出 -1全部
     * @param state 状态
     * @param userType 用户角色：0普通 1商家
     * @return String
     * @date 2018-2-25
     * @author lina
     */
    String queryOrderList(User user, Integer coinType, Integer orderType, Integer state, Integer userType, Integer page, Integer rows);

    /**
     * 查询订单详情
     * @param user
     * @param orderId
     * @return String
     * @date 2018-2-25
     * @author lina
     */
    String queryOrderInfo(User user, Integer orderId);

    /**
     * 取消订单
     * @param user
     * @param orderId
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    String cancelTakerOrder(User user, Integer orderId) ;

    /**
     * 确认付款
     * @param user
     * @param orderId
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    String confirmPayment(User user, Integer orderId, Integer payId, String password);

    /**
     * 确认收款
     * @param user
     * @param orderId
     * @param password
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    String confirmReceipt(User user, Integer orderId, String password);

    /**
     * 客服申诉
     * @param user
     * @param orderId
     * @return
     * @return String
     * @date 2018-2-27
     * @author lina
     */
    String orderAppeal(User user, Integer orderId,String remark);

    /**
     * 查询用户信息
     * @param user
     * @param userPhone
     * @return
     */
    String queryUserInfo(User user, String userPhone);
}
