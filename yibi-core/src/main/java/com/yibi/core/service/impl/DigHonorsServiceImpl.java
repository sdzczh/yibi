package com.yibi.core.service.impl;

import com.yibi.core.dao.DigHonorsMapper;
import com.yibi.core.entity.DigHonors;
import com.yibi.core.service.DigHonorsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑实现类:挖矿分区 dig_honors
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("digHonorsService")
public class DigHonorsServiceImpl implements DigHonorsService {
    @Resource
    private DigHonorsMapper digHonorsMapper;

    private static final Logger logger = LoggerFactory.getLogger(DigHonorsServiceImpl.class);

    @Override
    public int insert(DigHonors record) {
        return this.digHonorsMapper.insert(record);
    }

    @Override
    public int insertSelective(DigHonors record) {
        return this.digHonorsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(DigHonors record) {
        return this.digHonorsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DigHonors record) {
        return this.digHonorsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.digHonorsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DigHonors selectByPrimaryKey(Integer id) {
        return this.digHonorsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DigHonors> selectAll(Map<Object, Object> param) {
        return this.digHonorsMapper.selectAll(param);
    }

    @Override
    public List<DigHonors> selectPaging(Map<Object, Object> param) {
        return this.digHonorsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.digHonorsMapper.selectCount(param);
    }

    @Override
    public DigHonors selectByCalcul(Integer calcul) {
        return this.digHonorsMapper.selectByCalcul(calcul);
    }

    @Override
    public DigHonors selectByLevel(Integer level) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("rolegrade", level);

        List<DigHonors> result = selectAll(map);
        return result!=null&&!result.isEmpty()?result.get(0):null;
    }
}