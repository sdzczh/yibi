package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/16 0016.
 */
public class OrderTakerBizTest extends BaseTest {

    @Autowired
    private OrderTakerBiz orderTakerBiz;

    @Test
    public void takerReleaseDealTest(){
        User user = new User();
        user.setId(13);
        user.setPhone("15865711062");
        user.setIdstatus(1);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        try {
            String res = orderTakerBiz.takerReleaseDeal(user,0,new BigDecimal("0.167"),2,"123456",8);
            System.out.println(res);
        } catch (BanlanceNotEnoughException e) {
            System.out.println("余额不足");
            e.printStackTrace();
        }
    }

    @Test
    public void queryOrderListTest(){
        User user = new User();
        user.setId(8);
        String res = orderTakerBiz.queryOrderList(user,0,-1,-1,0,0,10);
        System.out.println(res);
    }

    @Test
    public void queryOrderInfoTest(){
        User user = new User();
        user.setId(5);
        String res = orderTakerBiz.queryOrderInfo(user,4);
        System.out.println(res);
    }

    @Test
    public void cancelTakerOrderTest(){
        User user = new User();
        user.setId(3);
        try {
            String res = orderTakerBiz.cancelTakerOrder(user,1);
            System.out.println(res);
        } catch (BanlanceNotEnoughException e) {
            System.out.println("余额不足");
            e.printStackTrace();
        }
    }

    @Test
    public void confirmPaymentTest(){
        User user = new User();
        user.setId(3);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        String res = orderTakerBiz.confirmPayment(user,5,0,"123456");
        System.out.println(res);
    }

    @Test
    public void confirmReceiptTest(){
        User user = new User();
        user.setId(5);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        String res = orderTakerBiz.confirmReceipt(user,4,"123456");
        System.out.println(res);
    }

    @Test
    public void orderAppealTest(){
        User user = new User();
        user.setId(5);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        String res = orderTakerBiz.orderAppeal(user,5,"");
        System.out.println(res);
    }

    @Test
    public void queryUserInfoTest(){
        User user = new User();
        user.setId(9);
        String res = orderTakerBiz.queryUserInfo(user,"15865711062");
        System.out.println(res);
    }
}
