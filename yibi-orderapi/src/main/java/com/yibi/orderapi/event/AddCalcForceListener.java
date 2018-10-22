package com.yibi.orderapi.event;

import com.google.common.eventbus.Subscribe;
import com.yibi.core.service.DigcalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 增加魂力
 */
@Slf4j
public class AddCalcForceListener {
    @Autowired
    private DigcalRecordService digcalRecordService;


    @Subscribe
    public void addCalcForce(AddCalcForceListenerBean event) {
        {
            Integer userId = event.getUserId();
            digcalRecordService.insertOrderCalculForce(userId);

        }
    }
}

