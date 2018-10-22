package com.yibi.core.service.impl;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.dao.OrderSpotRecordMapper;
import com.yibi.core.entity.OrderSpotRecord;
import com.yibi.core.service.OrderSpotRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:现货交易记录 order_spot_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("orderSpotRecordService")
public class OrderSpotRecordServiceImpl implements OrderSpotRecordService {
    @Resource
    private OrderSpotRecordMapper orderSpotRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderSpotRecordServiceImpl.class);

    @Override
    public int insert(OrderSpotRecord record) {
        return this.orderSpotRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderSpotRecord record) {
        return this.orderSpotRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderSpotRecord record) {
        return this.orderSpotRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderSpotRecord record) {
        return this.orderSpotRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderSpotRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderSpotRecord selectByPrimaryKey(Integer id) {
        return this.orderSpotRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderSpotRecord> selectAll(Map<Object, Object> param) {
        return this.orderSpotRecordMapper.selectAll(param);
    }

    @Override
    public List<OrderSpotRecord> selectPaging(Map<Object, Object> param) {
        return this.orderSpotRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderSpotRecordMapper.selectCount(param);
    }

    @Override
    public int queryUserCount(Integer userId) {
        return this.orderSpotRecordMapper.queryUserCount(userId);
    }

    @Override
    public BigDecimal getSumAmount(Integer orderCoinType, Integer unitCoinType, Date startTime, Date endTime) {
        Map<Object, Object> param = new HashMap<Object, Object>();
        if (orderCoinType != null) {
            param.put("ordercointype", orderCoinType);
        }
        if (unitCoinType != null) {
            param.put("unitcointype", unitCoinType);
        }
        if (startTime != null) {
            param.put("startTime", startTime);
        }
        if (endTime != null) {
            param.put("endTime", endTime);
        }
        return orderSpotRecordMapper.getSumAmount(param);
    }

    @Override
    public OrderSpotRecord getNewRecord(Integer unitCoinType, Integer orderCoinType, Date date) {
        Map<Object, Object> param = new HashMap<Object, Object>();
        param.put("unitcointype", unitCoinType);
        param.put("ordercointype", orderCoinType);
        param.put("date", date);
        return orderSpotRecordMapper.getNewRecord(param);
    }

    @Override
    public OrderSpotRecord getFirstRecord(Integer unitCoinType, Integer orderCoinType, Date date) {
        Map<Object, Object> param = new HashMap<Object, Object>();
        param.put("unitcointype", unitCoinType);
        param.put("ordercointype", orderCoinType);
        param.put("date", date);
        return orderSpotRecordMapper.getFirstRecord(param);
    }

    @Override
    public Map<String, Object> getHighLowAmount(Integer orderCoinType, Integer unitCoinType, Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("unitcointype", unitCoinType);
        param.put("ordercointype", orderCoinType);
        if (startTime != null) {
            param.put("startTime", startTime);
        }
        if (endTime != null) {
            param.put("endTime", endTime);
        }
        return orderSpotRecordMapper.getHighLowAmount(param);
    }

    @Override
    public List<OrderSpotRecord> queryOrderRecord(Integer orderId, Integer orderType) {
        Map<Object, Object> param = new HashMap<Object, Object>();
        if (orderType == GlobalParams.ORDER_TYPE_BUY){
            param.put("buyid",orderId);
        }else{
            param.put("saleid",orderId);
        }
        return this.selectAll(param);
    }
}