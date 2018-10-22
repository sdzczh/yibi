package com.yibi.core.service;

import com.yibi.core.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
public class OrderMakerServiceTest extends BaseTest {

    @Autowired
    private OrderMakerService orderMakerService;

    @Test
    public void queryAppListTest(){
        List<?> res = orderMakerService.queryAppList(3,0,0,-1,0,10);
        System.out.println(res.toString());
    }
}
