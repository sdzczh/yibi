package com.yibi.core.service.impl;

import com.yibi.core.dao.LoanRecordMapper;
import com.yibi.core.entity.LoanRecord;
import com.yibi.core.service.LoanRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:借款记录 loan_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("loanRecordService")
public class LoanRecordServiceImpl implements LoanRecordService {
    @Resource
    private LoanRecordMapper loanRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(LoanRecordServiceImpl.class);

    @Override
    public int insert(LoanRecord record) {
        return this.loanRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(LoanRecord record) {
        return this.loanRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(LoanRecord record) {
        return this.loanRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(LoanRecord record) {
        return this.loanRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.loanRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public LoanRecord selectByPrimaryKey(Integer id) {
        return this.loanRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LoanRecord> selectAll(Map<Object, Object> param) {
        return this.loanRecordMapper.selectAll(param);
    }

    @Override
    public List<LoanRecord> selectPaging(Map<Object, Object> param) {
        return this.loanRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.loanRecordMapper.selectCount(param);
    }
}