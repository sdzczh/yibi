package com.yibi.core.dao;

import com.yibi.core.entity.OrderMaker;
import com.yibi.core.entity.User;

import java.util.List;
import java.util.Map;

public interface OrderMakerMapper {
    int insert(OrderMaker record);

    int insertSelective(OrderMaker record);

    int updateByPrimaryKey(OrderMaker record);

    int updateByPrimaryKeySelective(OrderMaker record);

    int deleteByPrimaryKey(Integer id);

    OrderMaker selectByPrimaryKey(Integer id);

    List<OrderMaker> selectAll(Map<Object, Object> param);

    List<OrderMaker> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryAppList(Map<Object, Object> param);

    List<?> queryByType(Map<Object, Object> param);

    int updateOrderRemain(Map<Object, Object> param);

    List<OrderMaker> queryNotDealOrders(Map<Object, Object> param);

    List<Map<String,Object>> selectConditionPaging(Map<Object,Object> map);

    int selectConditionCount(Map<Object,Object> map);

}