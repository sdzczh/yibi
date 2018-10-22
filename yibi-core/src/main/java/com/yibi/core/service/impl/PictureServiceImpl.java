package com.yibi.core.service.impl;

import com.yibi.core.dao.PictureMapper;
import com.yibi.core.entity.Picture;
import com.yibi.core.service.PictureService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
    @Resource
    private PictureMapper pictureMapper;

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Override
    public int insert(Picture record) {
        return this.pictureMapper.insert(record);
    }

    @Override
    public int insertSelective(Picture record) {
        return this.pictureMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Picture record) {
        return this.pictureMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Picture record) {
        return this.pictureMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.pictureMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Picture selectByPrimaryKey(Integer id) {
        return this.pictureMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Picture> selectAll(Map<Object, Object> param) {
        return this.pictureMapper.selectAll(param);
    }

    @Override
    public List<Picture> selectPaging(Map<Object, Object> param) {
        return this.pictureMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.pictureMapper.selectCount(param);
    }
}