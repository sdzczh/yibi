package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class ChatGroupBizTest extends BaseTest {

    @Resource
    private ChatGroupBiz chatGroupBiz;
    @Test
    public void queryListTest(){
        String res = chatGroupBiz.queryList(0);
        System.out.println(res);
    }

    @Test
    public void queryGroupInfoTest(){
        User user = new User();
        user.setId(3);
        String res = chatGroupBiz.queryGroupInfo(user,"123qwe");
        System.out.println(res);
    }

    @Test
    public void joinGroupTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatGroupBiz.joinGroup(user,"123qwe",0);
        System.out.println(res);
    }

    @Test
    public void queryUsersTest(){
        User user = new User();
        user.setId(3);
        String res = chatGroupBiz.queryUsers(user,"123qwe",1,10);
        System.out.println(res);
    }

    @Test
    public void muteTest(){
        User user = new User();
        user.setId(3);
        user.setPhone("15621552297");
        String res = chatGroupBiz.mute(user,"123qwe");
        System.out.println(res);
    }



}
