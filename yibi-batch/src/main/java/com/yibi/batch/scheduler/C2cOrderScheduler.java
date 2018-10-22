package com.yibi.batch.scheduler;

import com.yibi.batch.biz.C2cOrderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/24 0024.
 * C2C交易定时
 */

@Component
public class C2cOrderScheduler {

    @Autowired
    private C2cOrderBiz c2cOrderBiz;


    /**
     * 定时取消订单
     */
    @Scheduled(cron="1/5 * * * * ?")
    public void cancelOrderSch(){
        c2cOrderBiz.startCancelUnpaidOrders();
    }


    /**
     * 定时确认订单
     */
    @Scheduled(cron="3/5 * * * * ?")
    public void confirmOrderSch(){
        c2cOrderBiz.startConfirmOrders();
    }
}
