package com.yibi.core.service.impl;

import com.yibi.core.dao.RobotTaskMapper;
import com.yibi.core.entity.RobotTask;
import com.yibi.core.service.RobotTaskService;
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
@Service("robotTaskService")
public class RobotTaskServiceImpl implements RobotTaskService {
    @Resource
    private RobotTaskMapper robotTaskMapper;

    private static final Logger logger = LoggerFactory.getLogger(RobotTaskServiceImpl.class);

    @Override
    public int insert(RobotTask record) {
        return this.robotTaskMapper.insert(record);
    }

    @Override
    public int insertSelective(RobotTask record) {
        return this.robotTaskMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(RobotTask record) {
        return this.robotTaskMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(RobotTask record) {
        return this.robotTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.robotTaskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public RobotTask selectByPrimaryKey(Integer id) {
        return this.robotTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RobotTask> selectAll(Map<Object, Object> param) {
        return this.robotTaskMapper.selectAll(param);
    }

    @Override
    public List<RobotTask> selectPaging(Map<Object, Object> param) {
        return this.robotTaskMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.robotTaskMapper.selectCount(param);
    }

    @Override
    public void updateTaskByCoinType(Map<String, Object> map) {
        this.robotTaskMapper.updateTaskByCoinType(map);
    }

    @Override
    public List<Map<String, Object>> selectTaskByRobotId(Map<Object, Object> map) {
        return this.robotTaskMapper.selectTaskByRobotId(map);
    }
}