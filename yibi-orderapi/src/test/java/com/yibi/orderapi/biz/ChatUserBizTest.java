package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/13 0013.
 */
public class ChatUserBizTest extends BaseTest{
    @Resource
    private ChatUserBiz chatUserBiz;

    @Test
    public void queryUserByAccountTest(){
        User user = new User();
        user.setId(3);
        String res = chatUserBiz.queryUserByAccount(user,"15621552298");
        System.out.println(res);
    }

    @Test
    public void addFriendTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatUserBiz.addFriend(user,"15621552298");
        System.out.println(res);
    }

    @Test
    public void queryAddFriendApplyTest(){
        User user = new User();
        user.setId(5);
        String res = chatUserBiz.queryAddFriendApply(user);
        System.out.println(res);
    }

    @Test
    public void checkFriendApplyTest(){
        User user = new User();
        user.setId(5);
        user.setPhone("15621552298");
        String res = chatUserBiz.checkFriendApply(user,1,1);
        System.out.println(res);
    }

    @Test
    public void queryListTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatUserBiz.queryList(user);
        System.out.println(res);
    }

    @Test
    public void getByPhoneTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatUserBiz.getByPhone(user,"15621552298");
        System.out.println(res);
    }

    @Test
    public void updateRemarkNameTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatUserBiz.updateRemarkName(user,"15621552298","小王");
        System.out.println(res);
    }
}
