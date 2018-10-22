package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class HomePageBizTest extends BaseTest {

    @Resource
    private HomePageBiz homePageBiz;
    @Resource
    private UserBiz userBiz;
    @Resource
    private BannerBiz bannerBiz;
    @Test
    public void queryUserTest(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }
    @Test
    public void systemBiz(){
        String user = homePageBiz.initOut();
        System.out.println(user);
    }
    @Test
    public void index(){
        User user = userBiz.queryUser();
        String s = homePageBiz.initIn(user);
        System.out.println(s);
    }
    @Test
    public void banner(){
        User user = userBiz.queryUser();
        String s = bannerBiz.getBannerByType(1);
        System.out.println(s);
    }

}
