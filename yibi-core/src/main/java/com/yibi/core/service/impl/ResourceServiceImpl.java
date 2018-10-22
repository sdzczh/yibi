package com.yibi.core.service.impl;

import com.yibi.core.dao.ResourceMapper;
import com.yibi.core.entity.Resource;
import com.yibi.core.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:资源 resource
 * 
 * @author: autogeneration
 * @date: 2018-07-10 15:12:25
 **/ 
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Override
    public int insert(Resource record) {
        return this.resourceMapper.insert(record);
    }

    @Override
    public int insertSelective(Resource record) {
        return this.resourceMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Resource record) {
        return this.resourceMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Resource record) {
        return this.resourceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Resource selectByPrimaryKey(Integer id) {
        return this.resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Resource> selectAll(Map<Object, Object> param) {
        return this.resourceMapper.selectAll(param);
    }

    @Override
    public List<Resource> selectPaging(Map<Object, Object> param) {
        return this.resourceMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.resourceMapper.selectCount(param);
    }

    @Override
    public List<Resource> selectRescourcesByManager(Map<String, Object> params) {
        return this.resourceMapper.selectRescourcesByManager(params);
    }

    @Override
    public Map<String, Object> getIndexInfo() {
        return this.resourceMapper.getIndexInfo();
    }
}