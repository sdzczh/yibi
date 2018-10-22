package com.yibi.core.service;

import com.yibi.core.entity.OrderMaker;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface OrderMakerService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(OrderMaker record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(OrderMaker record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(OrderMaker record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(OrderMaker record);

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
    OrderMaker selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderMaker> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderMaker> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询委托单列表
     * @param userId
     * @param orderType
     * @param coinType
     * @param state
     * @param page
     * @param rows
     * @return
     */
    List<?> queryAppList(Integer userId,Integer orderType,Integer coinType,Integer state,Integer page,Integer rows);


    /**
     * 查询全部商户委托列表
     * @param coinType
     * @param orderType
     * @param page
     * @param rows
     * @return
     */
    List<?> queryByType(Integer coinType, Integer orderType,Integer page,Integer rows);

    /**
     * 修改交易订单剩余密码
     * @param orderId
     * @param remainIncrement
     * @param frozenIncrement
     */
    void updateOrderRemain(Integer orderId, BigDecimal remainIncrement, BigDecimal frozenIncrement) throws BanlanceNotEnoughException;

    /**
     * 查询未成交的委托单
     * @param orderType
     * @param coinType
     * @param price
     * @return
     */
    List<OrderMaker> queryNotDealOrders(Integer orderType, Integer coinType, BigDecimal price);

    /**
     * 按照类型查询未成交的委托单
     * @param userId
     * @param orderType
     * @param state
     * @return
     */
    List<OrderMaker> queryByOrderTypeAndState(Integer userId, Integer orderType, Integer state);

    List<OrderMaker> queryUserOrderList(Integer userId,Integer orderType);

    /**
     * 后台条件查询
     * @param map
     * @return
     */
    List<Map<String,Object>> selectConditionPaging(Map<Object,Object> map);

    /**
     * 条件统计条数
     * @param map
     * @return
     */
    int selectConditionCount(Map<Object,Object> map);
}