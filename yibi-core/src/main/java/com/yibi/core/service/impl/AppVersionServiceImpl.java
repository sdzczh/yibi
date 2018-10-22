package com.yibi.core.service.impl;

import com.yibi.core.dao.AppVersionMapper;
import com.yibi.core.entity.AppVersion;
import com.yibi.core.service.AppVersionService;
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
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;

    private static final Logger logger = LoggerFactory.getLogger(AppVersionServiceImpl.class);

    @Override
    public int insert(AppVersion record) {
        return this.appVersionMapper.insert(record);
    }

    @Override
    public int insertSelective(AppVersion record) {
        return this.appVersionMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AppVersion record) {
        return this.appVersionMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AppVersion record) {
        return this.appVersionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.appVersionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AppVersion selectByPrimaryKey(Integer id) {
        return this.appVersionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AppVersion> selectAll(Map<Object, Object> param) {
        return this.appVersionMapper.selectAll(param);
    }

    @Override
    public List<AppVersion> selectPaging(Map<Object, Object> param) {
        return this.appVersionMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.appVersionMapper.selectCount(param);
    }

    @Override
    public List<AppVersion> getByVersionAndType(Integer version, Integer syetemType) {
        Map<Object, Object> map = new HashMap();
        map.put("version", version);
        map.put("phonetype", syetemType);
        List<AppVersion> users = selectAll(map);

        return users==null||users.isEmpty()?null:users;
    }
}