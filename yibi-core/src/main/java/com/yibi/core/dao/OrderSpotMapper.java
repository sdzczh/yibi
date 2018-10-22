package com.yibi.core.dao;

import com.yibi.core.entity.OrderSpot;
import java.util.List;
import java.util.Map;

public interface OrderSpotMapper {
    int insert(OrderSpot record);

    int insertSelective(OrderSpot record);

    int updateByPrimaryKey(OrderSpot record);

    int updateByPrimaryKeySelective(OrderSpot record);

    int deleteByPrimaryKey(Integer id);

    OrderSpot selectByPrimaryKey(Integer id);

    List<OrderSpot> selectAll(Map<Object, Object> param);

    List<OrderSpot> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<OrderSpot> selectAllMatching(Map<Object, Object> param);

    List<Map<String,Object>> selectConditionPaging(Map<Object,Object> map);

    int selectConditionCount(Map<Object, Object> map);


    List<Map<String, Object>> queryBuyOrderList(Map<String, Object> param);

    List<Map<String, Object>> querySaleOrderList(Map<String, Object> param);

    List<OrderSpot> selectOrderRecordPaging(Map<String, Object> param);

    int selectOrderRecordCount(Map<String, Object> param);

    List<OrderSpot> selectMatchedOrder(Map<String, Object> param);
}