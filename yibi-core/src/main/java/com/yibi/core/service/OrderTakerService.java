package com.yibi.core.service;

import com.yibi.core.entity.OrderTaker;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface OrderTakerService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(OrderTaker record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(OrderTaker record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(OrderTaker record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(OrderTaker record);

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
    OrderTaker selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderTaker> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderTaker> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询订单列表
     * @param userId
     * @param orderType
     * @param state
     * @param userType
     * @param coinType
     * @param page
     * @param rows
     * @return
     */
    List<?> queryAppList(Integer userId,Integer orderType,Integer state,Integer userType,Integer coinType,Integer page,Integer rows);

    int queryUserCount(Integer userId);

    /**
     * 条件查询记录
     * @param map
     * @return
     */
    List<Map<String,Object>> selectConditionPaging(Map<Object,Object> map);

    /**
     * 条件统计记录
     * @param map
     * @return
     */
    int selectConditionCount(Map<Object,Object> map);

    /**
     * 撤销订单
     * @param taker
     */
    void cancelOrder(OrderTaker taker,Integer operId,Integer state);

    /**
     * 确认订单
     * @param taker
     */
    void confirmOrder(OrderTaker taker,Integer operId);

    OrderTaker queryByFlagNum(String flagNum);
}