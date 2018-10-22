package com.yibi.core.service.impl;

import com.yibi.core.dao.DealDigRecordMapper;
import com.yibi.core.entity.DealDigRecord;
import com.yibi.core.entity.DealDigRecordModel;
import com.yibi.core.service.DealDigRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:交易挖矿记录 deal_dig_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("dealDigRecordService")
public class DealDigRecordServiceImpl implements DealDigRecordService {
    @Resource
    private DealDigRecordMapper dealDigRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(DealDigRecordServiceImpl.class);

    @Override
    public int insert(DealDigRecord record) {
        return this.dealDigRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(DealDigRecord record) {
        return this.dealDigRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(DealDigRecord record) {
        return this.dealDigRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DealDigRecord record) {
        return this.dealDigRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.dealDigRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DealDigRecord selectByPrimaryKey(Integer id) {
        return this.dealDigRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DealDigRecord> selectAll(Map<Object, Object> param) {
        return this.dealDigRecordMapper.selectAll(param);
    }

    @Override
    public List<DealDigRecord> selectPaging(Map<Object, Object> param) {
        return this.dealDigRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.dealDigRecordMapper.selectCount(param);
    }

    @Override
    public DealDigRecordModel queryProfit(Integer userid) {
        return this.dealDigRecordMapper.queryProfit(userid);
    }
}