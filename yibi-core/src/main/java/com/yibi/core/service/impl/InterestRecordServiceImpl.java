package com.yibi.core.service.impl;

import com.yibi.core.dao.InterestRecordMapper;
import com.yibi.core.entity.InterestRecord;
import com.yibi.core.service.InterestRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:利息记录 interest_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("interestRecordService")
public class InterestRecordServiceImpl implements InterestRecordService {
    @Resource
    private InterestRecordMapper interestRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(InterestRecordServiceImpl.class);

    @Override
    public int insert(InterestRecord record) {
        return this.interestRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(InterestRecord record) {
        return this.interestRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(InterestRecord record) {
        return this.interestRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(InterestRecord record) {
        return this.interestRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.interestRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public InterestRecord selectByPrimaryKey(Integer id) {
        return this.interestRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<InterestRecord> selectAll(Map<Object, Object> param) {
        return this.interestRecordMapper.selectAll(param);
    }

    @Override
    public List<InterestRecord> selectPaging(Map<Object, Object> param) {
        return this.interestRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.interestRecordMapper.selectCount(param);
    }
}