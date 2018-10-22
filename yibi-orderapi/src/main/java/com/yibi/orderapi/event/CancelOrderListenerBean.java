package com.yibi.orderapi.event;

import com.yibi.core.entity.OrderSpot;
import lombok.Data;

@Data
public class CancelOrderListenerBean {
    private OrderSpot orderSpot;
    private String flowOperType;
}
