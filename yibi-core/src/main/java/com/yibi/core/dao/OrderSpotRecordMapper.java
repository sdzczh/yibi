package com.yibi.core.dao;

import com.yibi.core.entity.OrderSpotRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderSpotRecordMapper {
    int insert(OrderSpotRecord record);

    int insertSelective(OrderSpotRecord record);

    int updateByPrimaryKey(OrderSpotRecord record);

    int updateByPrimaryKeySelective(OrderSpotRecord record);

    int deleteByPrimaryKey(Integer id);

    OrderSpotRecord selectByPrimaryKey(Integer id);

    List<OrderSpotRecord> selectAll(Map<Object, Object> param);

    List<OrderSpotRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    int queryUserCount(Integer userId );

    BigDecimal getSumAmount(Map<Object, Object> param);

    OrderSpotRecord getNewRecord(Map<Object, Object> param);

    OrderSpotRecord getFirstRecord(Map<Object, Object> param);

    Map<String, Object> getHighLowAmount(Map<String, Object> param);

}