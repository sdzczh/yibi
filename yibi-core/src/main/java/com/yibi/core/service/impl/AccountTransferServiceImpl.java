package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.AccountTransferMapper;
import com.yibi.core.entity.AccountTransfer;
import com.yibi.core.service.AccountTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
@Service("accountTransferService")
public class AccountTransferServiceImpl implements AccountTransferService {
    @Resource
    private AccountTransferMapper accountTransferMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountTransferServiceImpl.class);

    @Override
    public int insert(AccountTransfer record) {
        return this.accountTransferMapper.insert(record);
    }

    @Override
    public int insertSelective(AccountTransfer record) {
        return this.accountTransferMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AccountTransfer record) {
        return this.accountTransferMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AccountTransfer record) {
        return this.accountTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.accountTransferMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AccountTransfer selectByPrimaryKey(Integer id) {
        return this.accountTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AccountTransfer> selectAll(Map<Object, Object> param) {
        return this.accountTransferMapper.selectAll(param);
    }

    @Override
    public List<AccountTransfer> selectPaging(Map<Object, Object> param) {
        return this.accountTransferMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.accountTransferMapper.selectCount(param);
    }

    @Override
    public BigDecimal querySumByCoinAndTime(Integer userId, Integer fromAccountType, Integer toAccountType, Integer coinType, String startTime) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("fromAccountType",fromAccountType);
        map.put("toAccountType",toAccountType);
        map.put("coinType",coinType);
        map.put("startTime",startTime);
        return this.accountTransferMapper.querySumByCoinAndTime(map);
    }
}