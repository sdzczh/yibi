package com.yibi.core.dao;

import com.yibi.core.entity.OrderManage;
import java.util.List;
import java.util.Map;

public interface OrderManageMapper {
    int insert(OrderManage record);

    int insertSelective(OrderManage record);

    int updateByPrimaryKey(OrderManage record);

    int updateByPrimaryKeySelective(OrderManage record);

    int deleteByPrimaryKey(Integer id);

    OrderManage selectByPrimaryKey(Integer id);

    List<OrderManage> selectAll(Map<Object, Object> param);

    List<OrderManage> selectAllOrderBySeque(Map<Object, Object> param);

    List<OrderManage> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String,Object>> selectConditionPaging(Map<Object, Object> param);

    int selectConditionCount(Map<Object, Object> param);
}