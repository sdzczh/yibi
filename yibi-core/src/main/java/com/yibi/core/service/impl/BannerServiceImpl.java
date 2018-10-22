package com.yibi.core.service.impl;

import com.yibi.core.constants.GlobalParams;
import com.yibi.core.dao.BannerMapper;
import com.yibi.core.entity.Banner;
import com.yibi.core.service.BannerService;
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
 * @date: 2018-07-18 14:49:35
 **/ 
@Service("bannerService")
public class BannerServiceImpl implements BannerService {
    @Resource
    private BannerMapper bannerMapper;

    private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);

    @Override
    public int insert(Banner record) {
        return this.bannerMapper.insert(record);
    }

    @Override
    public int insertSelective(Banner record) {
        return this.bannerMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Banner record) {
        return this.bannerMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Banner record) {
        return this.bannerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.bannerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Banner selectByPrimaryKey(Integer id) {
        return this.bannerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Banner> selectAll(Map<Object, Object> param) {
        return this.bannerMapper.selectAll(param);
    }

    @Override
    public List<Banner> selectPaging(Map<Object, Object> param) {
        return this.bannerMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.bannerMapper.selectCount(param);
    }

    @Override
    public List<Banner> selectAllInfo(Map<Object, Object> param) {
        return this.bannerMapper.selectAllInfo(param);
    }

    @Override
    public List<Banner> selectBannerByType(Integer type) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("type", type);
        map.put("state", GlobalParams.ACTIVE);
        return this.selectAll(map);
    }
}