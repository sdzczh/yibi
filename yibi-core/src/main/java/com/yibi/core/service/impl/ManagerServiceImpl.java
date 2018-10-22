package com.yibi.core.service.impl;

import com.yibi.core.dao.ManagerMapper;
import com.yibi.core.entity.Manager;
import com.yibi.core.service.ManagerService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:管理员用户表 manager
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
    @Resource
    private ManagerMapper managerMapper;

    private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Override
    public int insert(Manager record) {
        return this.managerMapper.insert(record);
    }

    @Override
    public int insertSelective(Manager record) {
        return this.managerMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Manager record) {
        return this.managerMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Manager record) {
        return this.managerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.managerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Manager selectByPrimaryKey(Integer id) {
        return this.managerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Manager> selectAll(Map<Object, Object> param) {
        return this.managerMapper.selectAll(param);
    }

    @Override
    public List<Manager> selectPaging(Map<Object, Object> param) {
        return this.managerMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.managerMapper.selectCount(param);
    }
}