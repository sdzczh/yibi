package com.yibi.core.service.impl;

import com.yibi.core.dao.CommissionRecordMapper;
import com.yibi.core.entity.CommissionRecord;
import com.yibi.core.service.CommissionRecordService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:佣金表 commission_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("commissionRecordService")
public class CommissionRecordServiceImpl implements CommissionRecordService {
    @Resource
    private CommissionRecordMapper commissionRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(CommissionRecordServiceImpl.class);

    @Override
    public int insert(CommissionRecord record) {
        return this.commissionRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(CommissionRecord record) {
        return this.commissionRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommissionRecord record) {
        return this.commissionRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CommissionRecord record) {
        return this.commissionRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.commissionRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CommissionRecord selectByPrimaryKey(Integer id) {
        return this.commissionRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CommissionRecord> selectAll(Map<Object, Object> param) {
        return this.commissionRecordMapper.selectAll(param);
    }

    @Override
    public List<CommissionRecord> selectPaging(Map<Object, Object> param) {
        return this.commissionRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.commissionRecordMapper.selectCount(param);
    }

    @Override
    public BigDecimal selectSumAmountByOrderid(Integer orderid) {
        return commissionRecordMapper.selectSumAmountByOrderid(orderid);
    }
}