package com.yibi.core.service;

import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.OrderSpot;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:现货委托记录 order_spot
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface OrderSpotService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(OrderSpot record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(OrderSpot record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(OrderSpot record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(OrderSpot record);

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
    OrderSpot selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderSpot> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderSpot> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 获取匹配订单
     * @param param
     * @return
     */
    List<OrderSpot> selectAllMatching(Map<Object, Object> param);

    /**
     * 条件查询现货订单
     * @param map
     * @return
     */
    List<Map<String,Object>> selectConditionPaging(Map<Object,Object> map);

    /**
     * 条件统计记录数
     * @param map
     * @return
     */
    int selectConditionCount(Map<Object, Object> map);

    /**
     * 查询现货买入订单列表
     */
    List<?> queryBuyOrderList(Integer orderCoinType, Integer unitCoinType, Integer count, CoinScale coinScale);

    /**
     * 查询现货卖出订单列表
     */
    List<?> querySaleOrderList(Integer orderCoinType, Integer unitCoinType, Integer count, CoinScale coinScale);

    /**
     * 分页查询当前委托或历史记录
     */
    List<OrderSpot> selectOrderRecordPaging(Map<String, Object> param);

    /**
     * 分页查询当前委托或历史记录总数
     */
    int selectOrderRecordCount(Map<String, Object> param);

    /**
     * 查询已匹配到的订单
     */
    List<OrderSpot> selectMatchedOrder(Map<String, Object> param);
}