package com.yibi.core.dao;

import com.yibi.core.entity.OrderTaker;
import java.util.List;
import java.util.Map;

public interface OrderTakerMapper {
    int insert(OrderTaker record);

    int insertSelective(OrderTaker record);

    int updateByPrimaryKey(OrderTaker record);

    int updateByPrimaryKeySelective(OrderTaker record);

    int deleteByPrimaryKey(Integer id);

    OrderTaker selectByPrimaryKey(Integer id);

    List<OrderTaker> selectAll(Map<Object, Object> param);

    List<OrderTaker> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryAppList(Map<Object, Object> param);

    int queryUserCount(Integer userId);

    List<Map<String,Object>> selectConditionPaging(Map<Object,Object> map);

    int selectConditionCount(Map<Object,Object> map);



}