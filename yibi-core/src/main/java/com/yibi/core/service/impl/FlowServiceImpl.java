package com.yibi.core.service.impl;

import com.yibi.core.dao.FlowMapper;
import com.yibi.core.entity.Flow;
import com.yibi.core.service.FlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:交易流水 flow
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("flowService")
public class FlowServiceImpl implements FlowService {
    @Resource
    private FlowMapper flowMapper;

    private static final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);

    @Override
    public int insert(Flow record) {
        return this.flowMapper.insert(record);
    }

    @Override
    public int insertSelective(Flow record) {
        return this.flowMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Flow record) {
        return this.flowMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Flow record) {
        return this.flowMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.flowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Flow selectByPrimaryKey(Integer id) {
        return this.flowMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Flow> selectAll(Map<Object, Object> param) {
        return this.flowMapper.selectAll(param);
    }

    @Override
    public List<Flow> selectPaging(Map<Object, Object> param) {
        return this.flowMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.flowMapper.selectCount(param);
    }

    @Override
    public List<Flow> queryByUserIdAndCoinTypeAndAccountType(Integer userId, Integer coinType, Integer accountType, Integer page, Integer rows) {
        Map map = new HashMap();
        map.put("userid", userId);
        map.put("cointype", coinType);
        map.put("accounttype", accountType);
        map.put("firstResult", page*rows);
        map.put("maxResult", rows);
        return this.flowMapper.selectPagingForAccount(map);
    }

    @Override
    public List<Map<String, Object>> selectFlowOrPhone(Map<Object, Object> map) {
        return this.flowMapper.selectFlowOrPhone(map);
    }

    @Override
    public int selectFlowCount(Map<Object, Object> map) {
        return this.flowMapper.selectFlowCount(map);
    }
}