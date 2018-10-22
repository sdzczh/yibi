package com.yibi.core.service.impl;

import com.yibi.core.dao.BankMapper;
import com.yibi.core.entity.Bank;
import com.yibi.core.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:开户行表 bank
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
@Service("bankService")
public class BankServiceImpl implements BankService {
    @Resource
    private BankMapper bankMapper;

    private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Override
    public int insert(Bank record) {
        return this.bankMapper.insert(record);
    }

    @Override
    public int insertSelective(Bank record) {
        return this.bankMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Bank record) {
        return this.bankMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Bank record) {
        return this.bankMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.bankMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Bank selectByPrimaryKey(Integer id) {
        return this.bankMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Bank> selectAll(Map<Object, Object> param) {
        return this.bankMapper.selectAll(param);
    }

    @Override
    public List<Bank> selectPaging(Map<Object, Object> param) {
        return this.bankMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.bankMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectRelationPaging(Map<Object, Object> param) {
        return this.bankMapper.selectRelationPaging(param);
    }

    @Override
    public int selectRelationCount(Map<Object, Object> param) {
        return this.bankMapper.selectRelationCount(param);
    }
}