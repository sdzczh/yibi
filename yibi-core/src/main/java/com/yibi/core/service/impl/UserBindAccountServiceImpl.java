package com.yibi.core.service.impl;

import com.yibi.core.dao.UserBindAccountMapper;
import com.yibi.core.entity.UserBindAccount;
import com.yibi.core.service.UserBindAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:绑定账户表 user_bind_account
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("userBindAccountService")
public class UserBindAccountServiceImpl implements UserBindAccountService {
    @Resource
    private UserBindAccountMapper userBindAccountMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserBindAccountServiceImpl.class);

    @Override
    public int insert(UserBindAccount record) {
        return this.userBindAccountMapper.insert(record);
    }

    @Override
    public int insertSelective(UserBindAccount record) {
        return this.userBindAccountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserBindAccount record) {
        return this.userBindAccountMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UserBindAccount record) {
        return this.userBindAccountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.userBindAccountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UserBindAccount selectByPrimaryKey(Integer id) {
        return this.userBindAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserBindAccount> selectAll(Map<Object, Object> param) {
        return this.userBindAccountMapper.selectAll(param);
    }

    @Override
    public List<UserBindAccount> selectPaging(Map<Object, Object> param) {
        return this.userBindAccountMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.userBindAccountMapper.selectCount(param);
    }

    @Override
    public UserBindAccount selectByUserAndType(Map<Object, Object> params) {
        List<UserBindAccount> list = this.userBindAccountMapper.selectAll(params);
        return list == null  || list.isEmpty() ? null : list.get(0);
    }
}