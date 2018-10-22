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
public class ChatRedPacketBizTest extends BaseTest{

    @Resource
    private ChatRedPacketBiz chatRedPacketBiz;
    @Test
    public void sendRedPacketTest(){

        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        user.setIdstatus(1);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        try {
            String res = chatRedPacketBiz.sendRedPacket(user,1,0,new BigDecimal(1.2),1,"恭喜发财!","123456","15621552298");
            System.out.println(res);
        } catch (BanlanceNotEnoughException e) {
            System.out.println("余额不足");
            e.printStackTrace();
        }

    }

    @Test
    public void queryRedPacketDetailTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatRedPacketBiz.queryRedPacketDetail(user,1);
        System.out.println(res);

    }

    @Test
    public void reciveRedPacketTest(){
        User user = new User();
        user.setId(5);
        user.setPhone("15621552298");
        String res = chatRedPacketBiz.reciveRedPacket(user,2);
        System.out.println(res);
    }

    @Test
    public void queryRedPacketListTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatRedPacketBiz.queryRedPacketList(user,1,1,10);
        System.out.println(res);
    }
}
