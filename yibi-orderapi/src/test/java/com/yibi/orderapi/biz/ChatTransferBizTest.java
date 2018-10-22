package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/13 0013.
 */
public class ChatTransferBizTest extends BaseTest{

    @Resource
    private ChatTransferBiz chatTransferBiz;

    @Test
    public void sendFransferTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        user.setIdstatus(1);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        try {
            String res = chatTransferBiz.sendFransfer(user,"15621552298",0,1,new BigDecimal(1),"还钱","123456");
            System.out.println(res);
        } catch (BanlanceNotEnoughException e) {
            System.out.println("余额不足");
        }
    }

    @Test
    public void queryTransferDetailTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatTransferBiz.queryTransferDetail(user,4);
        System.out.println(res);
    }

    @Test
    public void queryTransferListTest(){
        User user = new User();
        user.setId(5);
        user.setPhone("15621552298");
        String res = chatTransferBiz.queryTransferList(user,0,0,10);
        System.out.println(res);
    }

}
