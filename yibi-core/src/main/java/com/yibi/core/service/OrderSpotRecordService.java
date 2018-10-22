package com.yibi.core.service;

import com.yibi.core.entity.OrderSpotRecord;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:现货交易记录 order_spot_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface OrderSpotRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(OrderSpotRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(OrderSpotRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(OrderSpotRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(OrderSpotRecord record);

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
    OrderSpotRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderSpotRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderSpotRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询用户订单总数
     * @param userId
     * @return
     */
    int queryUserCount(Integer userId);


    /**
     * 获取一段时间的成交总量
     *
     * @param orderCoinType
     * @param unitCoinType
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal getSumAmount(Integer orderCoinType, Integer unitCoinType, Date startTime, Date endTime);

    /**
     * 获取最近交易记录
     *
     * @param unitCoinType
     * @param orderCoinType
     * @return
     */
    OrderSpotRecord getNewRecord(Integer unitCoinType, Integer orderCoinType, Date date);

    OrderSpotRecord getFirstRecord(Integer unitCoinType, Integer orderCoinType, Date date);

    /**
     * 获取某段时间的最高价 最低价 成交量
     * @return
     */
    Map<String, Object> getHighLowAmount(Integer orderCoinType, Integer unitCoinType, Date startTime, Date endTime);

    /**
     * 获取交易成交记录
     * @param orderId
     * @param orderType
     * @return
     */
    List<OrderSpotRecord> queryOrderRecord(Integer orderId,Integer orderType);
}