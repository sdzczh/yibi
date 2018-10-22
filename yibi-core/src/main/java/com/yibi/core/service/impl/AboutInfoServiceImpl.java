package com.yibi.core.service.impl;

import com.yibi.core.dao.AboutInfoMapper;
import com.yibi.core.entity.AboutInfo;
import com.yibi.core.service.AboutInfoService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-18 16:54:11
 **/ 
@Service("aboutInfoService")
public class AboutInfoServiceImpl implements AboutInfoService {
    @Resource
    private AboutInfoMapper aboutInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(AboutInfoServiceImpl.class);

    @Override
    public int insert(AboutInfo record) {
        return this.aboutInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(AboutInfo record) {
        return this.aboutInfoMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(AboutInfo record) {
        return this.aboutInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AboutInfo record) {
        return this.aboutInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.aboutInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AboutInfo selectByPrimaryKey(Integer id) {
        return this.aboutInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<AboutInfo> selectAll(Map<Object, Object> param) {
        return this.aboutInfoMapper.selectAll(param);
    }

    @Override
    public List<AboutInfo> selectPaging(Map<Object, Object> param) {
        return this.aboutInfoMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.aboutInfoMapper.selectCount(param);
    }
}