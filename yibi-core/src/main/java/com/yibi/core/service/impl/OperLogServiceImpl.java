package com.yibi.core.service.impl;

import com.yibi.core.dao.OperLogMapper;
import com.yibi.core.entity.OperLog;
import com.yibi.core.service.OperLogService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:操作日志 oper_log
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("operLogService")
public class OperLogServiceImpl implements OperLogService {
    @Resource
    private OperLogMapper operLogMapper;

    private static final Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);

    @Override
    public int insert(OperLog record) {
        return this.operLogMapper.insert(record);
    }

    @Override
    public int insertSelective(OperLog record) {
        return this.operLogMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OperLog record) {
        return this.operLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OperLog record) {
        return this.operLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.operLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OperLog selectByPrimaryKey(Integer id) {
        return this.operLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OperLog> selectAll(Map<Object, Object> param) {
        return this.operLogMapper.selectAll(param);
    }

    @Override
    public List<OperLog> selectPaging(Map<Object, Object> param) {
        return this.operLogMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.operLogMapper.selectCount(param);
    }
}