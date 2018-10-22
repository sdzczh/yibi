package com.yibi.orderapi.biz.impl;

import com.yibi.core.entity.Bank;
import com.yibi.core.service.BankService;
import com.yibi.orderapi.biz.BankBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
public class BankBizImpl implements BankBiz{
    @Autowired
    private BankService bankService;

    @Override
    public String queryList(int page, int rows) {
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> data= new HashMap<>();
        map.put("firstResult", page);
        map.put("maxResult", rows);
        map.put("state", 0);
        List<Bank> list = bankService.selectPaging(map);
        data.put("data", list);
        return Result.toResult(ResultCode.SUCCESS,data);
    }
}
