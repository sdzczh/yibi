package com.yibi.core.service.impl;

import com.yibi.core.dao.RobotStatisticsMapper;
import com.yibi.core.entity.RobotStatistics;
import com.yibi.core.service.RobotStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:机器人交易统计 robot_statistics
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("robotStatisticsService")
public class RobotStatisticsServiceImpl implements RobotStatisticsService {
    @Resource
    private RobotStatisticsMapper robotStatisticsMapper;

    private static final Logger logger = LoggerFactory.getLogger(RobotStatisticsServiceImpl.class);

    @Override
    public int insert(RobotStatistics record) {
        return this.robotStatisticsMapper.insert(record);
    }

    @Override
    public int insertSelective(RobotStatistics record) {
        return this.robotStatisticsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(RobotStatistics record) {
        return this.robotStatisticsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(RobotStatistics record) {
        return this.robotStatisticsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.robotStatisticsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public RobotStatistics selectByPrimaryKey(Integer id) {
        return this.robotStatisticsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<RobotStatistics> selectAll(Map<Object, Object> param) {
        return this.robotStatisticsMapper.selectAll(param);
    }

    @Override
    public List<RobotStatistics> selectPaging(Map<Object, Object> param) {
        return this.robotStatisticsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.robotStatisticsMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectRelationPaging(Map<Object, Object> param) {
        return this.robotStatisticsMapper.selectRelationPaging(param);
    }

    @Override
    public int selectRelationCount(Map<Object, Object> param) {
        return this.robotStatisticsMapper.selectRelationCount(param);
    }
}