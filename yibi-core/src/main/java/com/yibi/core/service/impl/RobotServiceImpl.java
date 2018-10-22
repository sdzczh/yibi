package com.yibi.core.service.impl;

import com.yibi.core.dao.RobotMapper;
import com.yibi.core.entity.Robot;
import com.yibi.core.service.RobotService;
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
@Service("robotService")
public class RobotServiceImpl implements RobotService {
    @Resource
    private RobotMapper robotMapper;

    private static final Logger logger = LoggerFactory.getLogger(RobotServiceImpl.class);

    @Override
    public int insert(Robot record) {
        return this.robotMapper.insert(record);
    }

    @Override
    public int insertSelective(Robot record) {
        return this.robotMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Robot record) {
        return this.robotMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Robot record) {
        return this.robotMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.robotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Robot selectByPrimaryKey(Integer id) {
        return this.robotMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Robot> selectAll(Map<Object, Object> param) {
        return this.robotMapper.selectAll(param);
    }

    @Override
    public List<Robot> selectPaging(Map<Object, Object> param) {
        return this.robotMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.robotMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectRobotOrUserName() {
        return this.robotMapper.selectRobotOrUserName();
    }
}