package com.yibi.core.service.impl;

import com.yibi.core.dao.DocMapper;
import com.yibi.core.entity.Doc;
import com.yibi.core.service.DocService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:文档 doc
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("docService")
public class DocServiceImpl implements DocService {
    @Resource
    private DocMapper docMapper;

    private static final Logger logger = LoggerFactory.getLogger(DocServiceImpl.class);

    @Override
    public int insert(Doc record) {
        return this.docMapper.insert(record);
    }

    @Override
    public int insertSelective(Doc record) {
        return this.docMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Doc record) {
        return this.docMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Doc record) {
        return this.docMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.docMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Doc selectByPrimaryKey(Integer id) {
        return this.docMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Doc> selectAll(Map<Object, Object> param) {
        return this.docMapper.selectAll(param);
    }

    @Override
    public List<Doc> selectPaging(Map<Object, Object> param) {
        return this.docMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.docMapper.selectCount(param);
    }
}