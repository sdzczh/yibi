package com.yibi.core.service.impl;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.dao.UserMapper;
import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:用户表 user
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public int insert(User record) {
        return this.userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return this.userMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return this.userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return this.userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAll(Map<Object, Object> param) {
        return this.userMapper.selectAll(param);
    }

    @Override
    public List<User> selectPaging(Map<Object, Object> param) {
        return this.userMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.userMapper.selectCount(param);
    }

    @Override
    public User selectByPhone(String phone) {
        Map<Object, Object> map = new HashMap();
        map.put("phone", phone);
        List<User> users = selectAll(map);

        return users==null||users.isEmpty()?null:users.get(0);
    }

    @Override
    public User getByRole(Integer role) {
        Map<Object, Object> map = new HashMap();
        map.put("role", role);
        List<User> users = selectAll(map);

        return users==null||users.isEmpty()?null:users.get(0);
    }

    @Override
    public User getByidcard(String identificationnumber) {
        Map<Object, Object> map = new HashMap();
        map.put("idcard", identificationnumber);
        List<User> users = selectAll(map);

        return users==null||users.isEmpty()?null:users.get(0);
    }

    @Override
    public List<User> queryActiveUserList(Integer page, Integer rows) {
        Map<Object, Object> map = new HashMap();
        map.put("idStatus", GlobalParams.ACTIVE);
        map.put("state",1);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.selectPaging(map);
    }

}