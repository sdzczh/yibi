package com.yibi.orderapi.event;

import com.yibi.core.entity.CommissionRecord;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.entity.OrderSpotRecord;
import lombok.Data;

@Data
public class DealDigListenerBean {
    private CommissionRecord commissionRecord;
    private OrderManage manage;
    private OrderSpotRecord record;
    private Integer orderType;
}
