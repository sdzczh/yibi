package com.yibi.core.service.impl;

import com.yibi.core.dao.DealDigConfigMapper;
import com.yibi.core.entity.DealDigConfig;
import com.yibi.core.service.DealDigConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:交易挖矿配置 deal_dig_config
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("dealDigConfigService")
public class DealDigConfigServiceImpl implements DealDigConfigService {
    @Resource
    private DealDigConfigMapper dealDigConfigMapper;

    private static final Logger logger = LoggerFactory.getLogger(DealDigConfigServiceImpl.class);

    @Override
    public int insert(DealDigConfig record) {
        return this.dealDigConfigMapper.insert(record);
    }

    @Override
    public int insertSelective(DealDigConfig record) {
        return this.dealDigConfigMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(DealDigConfig record) {
        return this.dealDigConfigMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DealDigConfig record) {
        return this.dealDigConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.dealDigConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DealDigConfig selectByPrimaryKey(Integer id) {
        return this.dealDigConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DealDigConfig> selectAll(Map<Object, Object> param) {
        return this.dealDigConfigMapper.selectAll(param);
    }

    @Override
    public List<DealDigConfig> selectPaging(Map<Object, Object> param) {
        return this.dealDigConfigMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.dealDigConfigMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> selectConditionAll(Map<Object, Object> param) {
        return this.dealDigConfigMapper.selectConditionAll(param);
    }
    @Override
    public int selectConditionCount(Map<Object, Object> param) {
        return this.dealDigConfigMapper.selectConditionCount(param);
    }
}