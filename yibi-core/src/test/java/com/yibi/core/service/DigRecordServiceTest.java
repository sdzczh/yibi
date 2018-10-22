package com.yibi.core.service;

import com.yibi.core.BaseTest;
import com.yibi.core.entity.DigRecord;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2018/7/20 0020.
 */
public class DigRecordServiceTest extends BaseTest{
    @Resource
    private DigRecordService digRecordService;

    @Test
    public void selectByUserIdAndTypeTest(){
        List<DigRecord> list = digRecordService.selectByUserIdAndType(10,0);
        for (DigRecord ojb:list){
            System.out.println(ojb);
        }
    }

    @Test
    public void updateByPrimaryKeyTest(){
        DigRecord digRecord = new DigRecord();
        digRecord.setUserid(10);
        digRecord.setId(2);
        digRecord.setAmount(new BigDecimal(0.009));
        int i = digRecordService.updateByPrimaryKeySelective(digRecord);
        System.out.println(i);
    }

    @Test
    public void insertTest(){
        DigRecord digRecord = new DigRecord();
        digRecord.setUserid(10);
        digRecord.setCointype(0);
        digRecord.setState(0);
        digRecord.setAmount(new BigDecimal(0.3));
        digRecord.setCreatetime(new Timestamp(System.currentTimeMillis()));
        int i = digRecordService.insert(digRecord);
        System.out.println(digRecord);
    }

    @Test
    public void queryByUserTest(){
        List<?> list = digRecordService.queryByUser(10,0,10);
        for (Object ojb:list){
            System.out.println(ojb);
        }
    }
}
