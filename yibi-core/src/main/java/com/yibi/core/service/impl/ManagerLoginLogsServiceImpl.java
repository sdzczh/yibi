package com.yibi.core.service.impl;

import com.yibi.core.dao.ManagerLoginLogsMapper;
import com.yibi.core.entity.ManagerLoginLogs;
import com.yibi.core.service.ManagerLoginLogsService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("managerLoginLogsService")
public class ManagerLoginLogsServiceImpl implements ManagerLoginLogsService {
    @Resource
    private ManagerLoginLogsMapper managerLoginLogsMapper;

    private static final Logger logger = LoggerFactory.getLogger(ManagerLoginLogsServiceImpl.class);

    @Override
    public int insert(ManagerLoginLogs record) {
        return this.managerLoginLogsMapper.insert(record);
    }

    @Override
    public int insertSelective(ManagerLoginLogs record) {
        return this.managerLoginLogsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(ManagerLoginLogs record) {
        return this.managerLoginLogsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ManagerLoginLogs record) {
        return this.managerLoginLogsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.managerLoginLogsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ManagerLoginLogs selectByPrimaryKey(Integer id) {
        return this.managerLoginLogsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ManagerLoginLogs> selectAll(Map<Object, Object> param) {
        return this.managerLoginLogsMapper.selectAll(param);
    }

    @Override
    public List<ManagerLoginLogs> selectPaging(Map<Object, Object> param) {
        return this.managerLoginLogsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.managerLoginLogsMapper.selectCount(param);
    }
}