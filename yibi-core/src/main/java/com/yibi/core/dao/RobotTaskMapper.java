package com.yibi.core.dao;

import com.yibi.core.entity.RobotTask;

import java.util.List;
import java.util.Map;

public interface RobotTaskMapper {
    int insert(RobotTask record);

    int insertSelective(RobotTask record);

    int updateByPrimaryKey(RobotTask record);

    int updateByPrimaryKeySelective(RobotTask record);

    int deleteByPrimaryKey(Integer id);

    RobotTask selectByPrimaryKey(Integer id);

    List<RobotTask> selectAll(Map<Object, Object> param);

    List<RobotTask> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    void updateTaskByCoinType(Map<String,Object> map);

    List<Map<String,Object>> selectTaskByRobotId(Map<Object,Object> map);
}