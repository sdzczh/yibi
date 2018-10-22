package com.yibi.core.service.impl;

import com.yibi.core.dao.PosterMapper;
import com.yibi.core.entity.Poster;
import com.yibi.core.service.PosterService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-25 14:23:40
 **/ 
@Service("posterService")
public class PosterServiceImpl implements PosterService {
    @Resource
    private PosterMapper posterMapper;

    private static final Logger logger = LoggerFactory.getLogger(PosterServiceImpl.class);

    @Override
    public int insert(Poster record) {
        return this.posterMapper.insert(record);
    }

    @Override
    public int insertSelective(Poster record) {
        return this.posterMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Poster record) {
        return this.posterMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Poster record) {
        return this.posterMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.posterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Poster selectByPrimaryKey(Integer id) {
        return this.posterMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Poster> selectAll(Map<Object, Object> param) {
        return this.posterMapper.selectAll(param);
    }

    @Override
    public List<Poster> selectPaging(Map<Object, Object> param) {
        return this.posterMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.posterMapper.selectCount(param);
    }
}