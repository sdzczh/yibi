package com.yibi.web.service.impl;

import com.yibi.common.model.Grid;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.service.OrderTakerService;
import com.yibi.web.service.TakerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Service
public class TakerServiceImpl implements TakerServiceI {
    @Autowired
    private OrderTakerService orderTakerService;

    @Override
    public void cancelTake(Integer id, Integer operId) {
        this.orderTakerService.cancelOrder(this.orderTakerService.selectByPrimaryKey(id),operId, GlobalParams.C2C_ORDER_STATE_CANCEL);
    }
    @Override
    public void confirmTaker(Integer id, Integer operId) {
        this.orderTakerService.confirmOrder(this.orderTakerService.selectByPrimaryKey(id),operId);
    }
    @Override
    public Object makerListPage( Map<Object,Object> map) {
        Grid grid = new Grid();
        grid.setTotal(this.orderTakerService.selectConditionCount(map));
        grid.setRows(this.orderTakerService.selectConditionPaging(map));
        return grid;
    }
}
