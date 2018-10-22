package com.yibi.core.service.impl;

import com.yibi.core.dao.DigBaseMapper;
import com.yibi.core.entity.DigBase;
import com.yibi.core.service.DigBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("digBaseService")
public class DigBaseServiceImpl implements DigBaseService {
    @Resource
    private DigBaseMapper digBaseMapper;

    private static final Logger logger = LoggerFactory.getLogger(DigBaseServiceImpl.class);

    @Override
    public int insert(DigBase record) {
        return this.digBaseMapper.insert(record);
    }

    @Override
    public int insertSelective(DigBase record) {
        return this.digBaseMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(DigBase record) {
        return this.digBaseMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DigBase record) {
        return this.digBaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.digBaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DigBase selectByPrimaryKey(Integer id) {
        return this.digBaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DigBase> selectAll(Map<Object, Object> param) {
        return this.digBaseMapper.selectAll(param);
    }

    @Override
    public List<DigBase> selectPaging(Map<Object, Object> param) {
        return this.digBaseMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.digBaseMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectAllByCondtion() {
        return this.digBaseMapper.selectAllByCondtion();
    }
}