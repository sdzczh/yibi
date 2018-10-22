package com.yibi.core.service.impl;

import com.yibi.core.dao.OrderManageMapper;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.service.OrderManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:交易管理 order_manage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("orderManageService")
public class OrderManageServiceImpl implements OrderManageService {
    @Resource
    private OrderManageMapper orderManageMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderManageServiceImpl.class);

    @Override
    public int insert(OrderManage record) {
        return this.orderManageMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderManage record) {
        return this.orderManageMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderManage record) {
        return this.orderManageMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderManage record) {
        return this.orderManageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderManageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderManage selectByPrimaryKey(Integer id) {
        return this.orderManageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderManage> selectAll(Map<Object, Object> param) {
        return this.orderManageMapper.selectAll(param);
    }

    @Override
    public List<OrderManage> selectAllOrderBySeque(Map<Object, Object> param) {
        return this.orderManageMapper.selectAllOrderBySeque(param);
    }

    @Override
    public List<OrderManage> selectPaging(Map<Object, Object> param) {
        return this.orderManageMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderManageMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectConditionPaging(Map<Object, Object> param) {
        return this.orderManageMapper.selectConditionPaging(param);
    }

    @Override
    public int selectConditionCount(Map<Object, Object> param) {
        return this.orderManageMapper.selectConditionCount(param);
    }
}