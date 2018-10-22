package com.yibi.core.service.impl;

import com.yibi.core.dao.RoleResourceMapper;
import com.yibi.core.entity.RoleResource;
import com.yibi.core.service.RoleResourceService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:角色资源关联表 role_resource
 * 
 * @author: autogeneration
 * @date: 2018-07-10 15:12:25
 **/ 
@Service("roleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoleResourceServiceImpl.class);

    @Override
    public int insert(RoleResource record) {
        return this.roleResourceMapper.insert(record);
    }

    @Override
    public int insertSelective(RoleResource record) {
        return this.roleResourceMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(RoleResource record) {
        return this.roleResourceMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(RoleResource record) {
        return this.roleResourceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.roleResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public RoleResource selectByPrimaryKey(Integer id) {
        return this.roleResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RoleResource> selectAll(Map<Object, Object> param) {
        return this.roleResourceMapper.selectAll(param);
    }

    @Override
    public List<RoleResource> selectPaging(Map<Object, Object> param) {
        return this.roleResourceMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.roleResourceMapper.selectCount(param);
    }
}