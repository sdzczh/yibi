package com.yibi.core.service.impl;

import com.yibi.core.dao.RoleMapper;
import com.yibi.core.entity.Role;
import com.yibi.core.service.RoleService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:角色表 role
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public int insert(Role record) {
        return this.roleMapper.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return this.roleMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return this.roleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return this.roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return this.roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> selectAll(Map<Object, Object> param) {
        return this.roleMapper.selectAll(param);
    }

    @Override
    public List<Role> selectPaging(Map<Object, Object> param) {
        return this.roleMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.roleMapper.selectCount(param);
    }


}