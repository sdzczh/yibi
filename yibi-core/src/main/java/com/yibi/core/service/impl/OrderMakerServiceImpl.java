package com.yibi.core.service.impl;

import com.google.common.collect.Maps;
import com.yibi.core.dao.OrderMakerMapper;
import com.yibi.core.entity.OrderMaker;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.OrderMakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("orderMakerService")
public class OrderMakerServiceImpl implements OrderMakerService {
    @Resource
    private OrderMakerMapper orderMakerMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderMakerServiceImpl.class);

    @Override
    public int insert(OrderMaker record) {
        return this.orderMakerMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderMaker record) {
        return this.orderMakerMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderMaker record) {
        return this.orderMakerMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderMaker record) {
        return this.orderMakerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderMakerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderMaker selectByPrimaryKey(Integer id) {
        return this.orderMakerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderMaker> selectAll(Map<Object, Object> param) {
        return this.orderMakerMapper.selectAll(param);
    }

    @Override
    public List<OrderMaker> selectPaging(Map<Object, Object> param) {
        return this.orderMakerMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderMakerMapper.selectCount(param);
    }

    @Override
    public List<?> queryAppList(Integer userId, Integer orderType, Integer coinType, Integer state, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userId",userId);
        map.put("orderType",orderType);
        map.put("coinType",coinType);
        map.put("state",state);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);

        return this.orderMakerMapper.queryAppList(map);
    }

    @Override
    public List<?> queryByType(Integer coinType, Integer orderType, Integer page, Integer rows) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("orderType",orderType);
        map.put("coinType",coinType);
        map.put("firstResult",page*rows);
        map.put("maxResult",rows);
        return this.orderMakerMapper.queryByType(map);
    }

    @Override
    public void updateOrderRemain(Integer orderId, BigDecimal remainIncrement, BigDecimal frozenIncrement) throws BanlanceNotEnoughException {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("orderId",orderId);
        map.put("remainIncrement",remainIncrement);
        map.put("frozenIncrement",frozenIncrement);

        int i = this.orderMakerMapper.updateOrderRemain(map);
        if(i!=1){
            throw new BanlanceNotEnoughException("发布交易-商户委托单剩余数量不足");
        }
    }

    @Override
    public List<OrderMaker> queryNotDealOrders(Integer orderType, Integer coinType, BigDecimal price) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("orderType",orderType);
        map.put("coinType",coinType);
        map.put("price",price);
        return this.orderMakerMapper.queryNotDealOrders(map);
    }

    @Override
    public List<OrderMaker> queryByOrderTypeAndState(Integer userId, Integer orderType, Integer state) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userid",userId);
        map.put("type",orderType);
        map.put("state",state);
        return this.orderMakerMapper.selectAll(map);
    }

    @Override
    public List<OrderMaker> queryUserOrderList(Integer userId,Integer orderType) {
        Map<Object,Object> map = Maps.newHashMap();
        map.put("userid",userId);
        map.put("orderflag",1);
        map.put("state",0);
        map.put("type",orderType);
        return this.selectAll(map);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> map) {
        return this.orderMakerMapper.selectConditionPaging(map);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> map) {
        return this.orderMakerMapper.selectConditionCount(map);
    }
}