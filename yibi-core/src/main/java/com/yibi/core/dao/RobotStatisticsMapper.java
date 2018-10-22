package com.yibi.core.dao;

import com.yibi.core.entity.RobotStatistics;
import java.util.List;
import java.util.Map;

public interface RobotStatisticsMapper {
    int insert(RobotStatistics record);

    int insertSelective(RobotStatistics record);

    int updateByPrimaryKey(RobotStatistics record);

    int updateByPrimaryKeySelective(RobotStatistics record);

    int deleteByPrimaryKey(Integer id);

    RobotStatistics selectByPrimaryKey(Integer id);

    List<RobotStatistics> selectAll(Map<Object, Object> param);

    List<RobotStatistics> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);


    List<Map<String,Object>> selectRelationPaging(Map<Object, Object> param);

    int selectRelationCount(Map<Object, Object> param);
}