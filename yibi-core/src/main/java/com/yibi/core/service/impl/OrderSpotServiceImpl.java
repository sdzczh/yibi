package com.yibi.core.service.impl;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.dao.OrderSpotMapper;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.OrderSpot;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.OrderSpotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:现货委托记录 order_spot
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("orderSpotService")
public class OrderSpotServiceImpl implements OrderSpotService {
    @Resource
    private OrderSpotMapper orderSpotMapper;
    @Autowired
    private CoinScaleService coinScaleService;

    private static final Logger logger = LoggerFactory.getLogger(OrderSpotServiceImpl.class);

    @Override
    public int insert(OrderSpot record) {
        return this.orderSpotMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderSpot record) {
        return this.orderSpotMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderSpot record) {
        return this.orderSpotMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderSpot record) {
        return this.orderSpotMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderSpotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderSpot selectByPrimaryKey(Integer id) {
        return this.orderSpotMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderSpot> selectAll(Map<Object, Object> param) {
        return this.orderSpotMapper.selectAll(param);
    }

    @Override
    public List<OrderSpot> selectPaging(Map<Object, Object> param) {
        return this.orderSpotMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderSpotMapper.selectCount(param);
    }

    @Override
    public List<OrderSpot> selectAllMatching(Map<Object, Object> param) {
        return this.orderSpotMapper.selectAllMatching(param);
    }

    @Override
    public List<?> queryBuyOrderList(Integer orderCoinType, Integer unitCoinType, Integer count, CoinScale coinScale) {
        Map<String, Object> param = new HashMap<>();
        param.put("ordercointype", orderCoinType);
        param.put("unitcointype", unitCoinType);
        param.put("count", count);
        param.put("type", GlobalParams.ORDER_TYPE_BUY);
        param.put("state", GlobalParams.ORDER_STATE_UNTREATED);
        param.put("ordertype", GlobalParams.ORDER_ORDERTYPE_LIMIT);
        List<Map<String, Object>> list = orderSpotMapper.queryBuyOrderList(param);

        return list;
    }

    @Override
    public List<?> querySaleOrderList(Integer orderCoinType, Integer unitCoinType, Integer count, CoinScale coinScale) {
        Map<String, Object> param = new HashMap<>();
        param.put("ordercointype", orderCoinType);
        param.put("unitcointype", unitCoinType);
        param.put("type", GlobalParams.ORDER_TYPE_SALE);
        param.put("state", GlobalParams.ORDER_STATE_UNTREATED);
        param.put("ordertype", GlobalParams.ORDER_ORDERTYPE_LIMIT);
        param.put("count", count);
        List<Map<String, Object>> list = orderSpotMapper.querySaleOrderList(param);

        return list;
    }

    @Override
    public List<OrderSpot> selectOrderRecordPaging(Map<String, Object> param) {
        return orderSpotMapper.selectOrderRecordPaging(param);
    }

    @Override
    public int selectOrderRecordCount(Map<String, Object> param) {
        return orderSpotMapper.selectOrderRecordCount(param);
    }

    @Override
    public List<OrderSpot> selectMatchedOrder(Map<String, Object> param) {
        return orderSpotMapper.selectMatchedOrder(param);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> map) {
        return this.orderSpotMapper.selectConditionPaging(map);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> map) {
        return this.orderSpotMapper.selectConditionCount(map);
    }
}