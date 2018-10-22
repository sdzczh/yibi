package com.yibi.core.service.impl;

import com.yibi.core.dao.DictionaryMapper;
import com.yibi.core.entity.Dictionary;
import com.yibi.core.service.DictionaryService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:字典表 dictionary
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
    @Resource
    private DictionaryMapper dictionaryMapper;

    private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Override
    public int insert(Dictionary record) {
        return this.dictionaryMapper.insert(record);
    }

    @Override
    public int insertSelective(Dictionary record) {
        return this.dictionaryMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Dictionary record) {
        return this.dictionaryMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Dictionary record) {
        return this.dictionaryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.dictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Dictionary selectByPrimaryKey(Integer id) {
        return this.dictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Dictionary> selectAll(Map<Object, Object> param) {
        return this.dictionaryMapper.selectAll(param);
    }

    @Override
    public List<Dictionary> selectPaging(Map<Object, Object> param) {
        return this.dictionaryMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.dictionaryMapper.selectCount(param);
    }
}