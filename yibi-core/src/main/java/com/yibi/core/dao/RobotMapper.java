package com.yibi.core.dao;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.yibi.core.entity.Robot;
import java.util.List;
import java.util.Map;

public interface RobotMapper {
    int insert(Robot record);

    int insertSelective(Robot record);

    int updateByPrimaryKey(Robot record);

    int updateByPrimaryKeySelective(Robot record);

    int deleteByPrimaryKey(Integer id);

    Robot selectByPrimaryKey(Integer id);

    List<Robot> selectAll(Map<Object, Object> param);

    List<Robot> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectRobotOrUserName();
}