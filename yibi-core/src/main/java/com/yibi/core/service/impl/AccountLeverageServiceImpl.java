package com.yibi.core.service.impl;

import com.yibi.core.dao.AccountLeverageMapper;
import com.yibi.core.entity.AccountLeverage;
import com.yibi.core.service.AccountLeverageService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:杠杆账户 account_leverage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
@Service("accountLeverageService")
public class AccountLeverageServiceImpl implements AccountLeverageService {
    @Resource
    private AccountLeverageMapper accountLeverageMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountLeverageServiceImpl.class);

    @Override
    public int insert(AccountLeverage record) {
        return this.accountLeverageMapper.insert(record);
    }

    @Override
    public int insertSelective(AccountLeverage record) {
        return this.accountLeverageMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AccountLeverage record) {
        return this.accountLeverageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AccountLeverage record) {
        return this.accountLeverageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.accountLeverageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AccountLeverage selectByPrimaryKey(Integer id) {
        return this.accountLeverageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AccountLeverage> selectAll(Map<Object, Object> param) {
        return this.accountLeverageMapper.selectAll(param);
    }

    @Override
    public List<AccountLeverage> selectPaging(Map<Object, Object> param) {
        return this.accountLeverageMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.accountLeverageMapper.selectCount(param);
    }
}