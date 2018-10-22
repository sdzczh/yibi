package com.yibi.core.service.impl;

import com.yibi.core.dao.BindInfoMapper;
import com.yibi.core.entity.BindInfo;
import com.yibi.core.service.BindInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
@Service("bindInfoService")
public class BindInfoServiceImpl implements BindInfoService {
    @Resource
    private BindInfoMapper bindInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(BindInfoServiceImpl.class);

    @Override
    public int insert(BindInfo record) {
        return this.bindInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(BindInfo record) {
        return this.bindInfoMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(BindInfo record) {
        return this.bindInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BindInfo record) {
        return this.bindInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.bindInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public BindInfo selectByPrimaryKey(Integer id) {
        return this.bindInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BindInfo> selectAll(Map<Object, Object> param) {
        return this.bindInfoMapper.selectAll(param);
    }

    @Override
    public List<BindInfo> selectPaging(Map<Object, Object> param) {
        return this.bindInfoMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.bindInfoMapper.selectCount(param);
    }

    @Override
    public List<BindInfo> queryByUser(Integer userId) {
        Map<Object,Object> map = new HashMap<>();
        map.put("userid",userId);
        map.put("state",1);
        return bindInfoMapper.selectAll(map);
    }

    @Override
    public BindInfo queryByUserAndType(Integer userId, Integer type) {
        Map<Object,Object> map = new HashMap<>();
        map.put("userid",userId);
        map.put("type",type);
        map.put("state",1);
        List<BindInfo> list = this.bindInfoMapper.selectAll(map);
        return list ==null||list.isEmpty()?null:list.get(0);
    }

    @Override
    public int updateOrInsertBindInfo(BindInfo bind) {
        Map<Object,Object> map = new HashMap<>();
        map.put("userid", bind.getUserid());
        map.put("type", bind.getType());
        map.put("state", bind.getState());
        List<BindInfo> list = this.bindInfoMapper.selectAll(map);
        if(!list.isEmpty() && list != null){
            bind.setId(list.get(0).getId());
            return this.updateByPrimaryKey(bind);
        }
        return this.insertSelective(bind);
    }
}