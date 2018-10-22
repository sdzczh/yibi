package com.yibi.core.service;

import com.yibi.core.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/7/31 0031.
 */
public class OrderSpotRecordServiceTest extends BaseTest{

    @Autowired
    private OrderSpotRecordService orderSpotRecordService;

    @Test
    public void queryOrderRecordTest(){
        System.out.println(orderSpotRecordService.queryOrderRecord(50,0));
    }
}
