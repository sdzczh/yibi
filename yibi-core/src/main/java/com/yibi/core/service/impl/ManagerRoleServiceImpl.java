package com.yibi.core.service.impl;

import com.yibi.core.dao.ManagerRoleMapper;
import com.yibi.core.entity.ManagerRole;
import com.yibi.core.service.ManagerRoleService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:用户角色关联表 manager_role
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("managerRoleService")
public class ManagerRoleServiceImpl implements ManagerRoleService {
    @Resource
    private ManagerRoleMapper managerRoleMapper;

    private static final Logger logger = LoggerFactory.getLogger(ManagerRoleServiceImpl.class);

    @Override
    public int insert(ManagerRole record) {
        return this.managerRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(ManagerRole record) {
        return this.managerRoleMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ManagerRole record) {
        return this.managerRoleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ManagerRole record) {
        return this.managerRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.managerRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ManagerRole selectByPrimaryKey(Integer id) {
        return this.managerRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ManagerRole> selectAll(Map<Object, Object> param) {
        return this.managerRoleMapper.selectAll(param);
    }

    @Override
    public List<ManagerRole> selectPaging(Map<Object, Object> param) {
        return this.managerRoleMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.managerRoleMapper.selectCount(param);
    }

    @Override
    public List<Integer> findRoleIdListByManagerId(Integer managerId) {
        return this.managerRoleMapper.findRoleIdListByManagerId(managerId);
    }
}