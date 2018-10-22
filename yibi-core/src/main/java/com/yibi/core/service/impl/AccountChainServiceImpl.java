package com.yibi.core.service.impl;

import com.yibi.core.dao.AccountChainMapper;
import com.yibi.core.entity.AccountChain;
import com.yibi.core.entity.User;
import com.yibi.core.service.AccountChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
@Service("accountChainService")
public class AccountChainServiceImpl implements AccountChainService {
    @Resource
    private AccountChainMapper accountChainMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountChainServiceImpl.class);

    @Override
    public int insert(AccountChain record) {
        return this.accountChainMapper.insert(record);
    }

    @Override
    public int insertSelective(AccountChain record) {
        return this.accountChainMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AccountChain record) {
        return this.accountChainMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AccountChain record) {
        return this.accountChainMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.accountChainMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AccountChain selectByPrimaryKey(Integer id) {
        return this.accountChainMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AccountChain> selectAll(Map<Object, Object> param) {
        return this.accountChainMapper.selectAll(param);
    }

    @Override
    public List<AccountChain> selectPaging(Map<Object, Object> param) {
        return this.accountChainMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.accountChainMapper.selectCount(param);
    }

    @Override
    public List<AccountChain> getChainList(Integer type, int page, int rows) {
        Map<Object, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("firstResult", page*rows);
        map.put("maxResult", rows);
        return this.selectPaging(map);
    }

    @Override
    public AccountChain changeSize(User user, Integer type) {

        AccountChain acc = new AccountChain();
        acc.setUserid(user.getId());
        acc.setType(type);
        this.accountChainMapper.updateSize(acc);
        return acc;
    }
}