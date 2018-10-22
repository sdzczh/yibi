package com.yibi.batch.biz;

/**
 * Created by Administrator on 2018/7/24 0024.
 */
public interface C2cOrderBiz {

    /**
     * 定时撤销未付款的C2C订单
     * @return void
     * @date 2018-3-5
     * @author lina
     */
    void startCancelUnpaidOrders();

    /**
     * 定时确认收款
     * @return void
     * @date 2018-3-6
     * @author lina
     */
    void startConfirmOrders();
}
