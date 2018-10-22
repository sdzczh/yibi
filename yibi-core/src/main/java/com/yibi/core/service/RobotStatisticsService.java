package com.yibi.core.service;

import com.yibi.core.entity.RobotStatistics;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:机器人交易统计 robot_statistics
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface RobotStatisticsService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(RobotStatistics record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(RobotStatistics record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(RobotStatistics record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(RobotStatistics record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    RobotStatistics selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<RobotStatistics> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<RobotStatistics> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 后台关联币种表查询机器人挂单统计
     * @param param
     * @return
     */
    List<Map<String,Object>> selectRelationPaging(Map<Object, Object> param);

    /**
     * 后台关联币种表，统计查询
     * @param param
     * @return
     */
    int selectRelationCount(Map<Object, Object> param);
}