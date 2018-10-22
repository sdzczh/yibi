package com.yibi.core.service;

import com.yibi.core.entity.DigcalRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:算力记录 digcal_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface DigcalRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(DigcalRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(DigcalRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(DigcalRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(DigcalRecord record);

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
    DigcalRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<DigcalRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<DigcalRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询当天是否存在指定类型的算力
     * @param userId
     * @param code
     * @return
     */
    boolean existCalcalForceDay(Integer userId, Integer code);

    /**
     * 查询指定时间段内，是否存在指定类型的算力
     * @param userId
     * @param code
     * @param startTime
     * @param endTime
     * @return
     */
    boolean existCalcalForceDay(Integer userId, Integer code, Timestamp startTime, Timestamp endTime);

    Integer getTotalCalculForceByUserAndType(Integer userId, Integer code);

    boolean existCalcalForce(Integer userId, Integer type);

    /**
     * 查询用户魂力流水记录
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    List<?>  queryListByUser(Integer userId,Integer page,Integer rows);

    /**
     * 增加交易魂力
     * @param userId
     */
    void insertOrderCalculForce(Integer userId);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBatch(List<DigcalRecord> list);

    /**
     * 查询当天的增加交易魂力的记录
     * @param userId
     * @return
     */
    int selectTodayCountByUserId(Integer userId);
}