package com.yibi.orderapi.event;

import com.google.common.eventbus.Subscribe;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.OrderSpot;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.OrderSpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 撤销现货订单
 */
@Slf4j
public class CancelOrderListener {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderSpotService orderSpotService;
    @Autowired
    private CoinScaleService coinScaleService;

    @Subscribe
    public void cancelOrder(CancelOrderListenerBean event) {
        OrderSpot orderSpot = event.getOrderSpot();
        CoinScale scale = coinScaleService.queryByCoin(orderSpot.getOrdercointype(),orderSpot.getUnitcointype());
        log.info("撤销现货订单--->" + orderSpot.toString());
        Integer cancelCoinType = null;
        BigDecimal amount;
        if (orderSpot.getType() == GlobalParams.ORDER_TYPE_BUY) {
            cancelCoinType = orderSpot.getUnitcointype();
            amount = BigDecimalUtils.multiply(orderSpot.getRemain(),orderSpot.getPrice(),scale.getOrderamtpricescale());
        }else{
            cancelCoinType = orderSpot.getOrdercointype();
            amount = orderSpot.getRemain();
        }
        //orderSpot.setRemain(new BigDecimal(0));
        orderSpot.setState(GlobalParams.ORDER_STATE_BACK);
        orderSpotService.updateByPrimaryKeySelective(orderSpot);
        if (orderSpot.getLevflag() == 1) {
            //TODO 如果是杠杆账户挂单,退款到杠杆账户
        }
        if (orderSpot.getLevflag() == 0) {
            //如果是现货账户挂单，退款到现货账户
            try {
                accountService.updateAccountAndInsertFlow(orderSpot.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, cancelCoinType, amount, new BigDecimal(0), GlobalParams.SYSTEM_OPERID, event.getFlowOperType(), orderSpot.getId());
            } catch (BanlanceNotEnoughException e) {
                log.error("撤销订单失败--->"+e.getMessage());
            }
        }
    }




}

