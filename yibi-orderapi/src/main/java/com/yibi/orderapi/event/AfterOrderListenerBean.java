package com.yibi.orderapi.event;

import com.yibi.core.entity.OrderSpotRecord;
import lombok.Data;

import java.util.List;

@Data
public class AfterOrderListenerBean {
    private Integer orderCoin;
    private Integer unitCoin;
    private List<OrderSpotRecord> recordList;
}
