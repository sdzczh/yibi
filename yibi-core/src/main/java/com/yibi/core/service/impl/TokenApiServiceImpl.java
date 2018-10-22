package com.yibi.core.service.impl;

import com.yibi.core.dao.TokenApiMapper;
import com.yibi.core.entity.TokenApi;
import com.yibi.core.service.TokenApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-08-14 17:20:32
 **/ 
@Service("tokenApiService")
public class TokenApiServiceImpl implements TokenApiService {
    @Resource
    private TokenApiMapper tokenApiMapper;

    private static final Logger logger = LoggerFactory.getLogger(TokenApiServiceImpl.class);

    @Override
    public int insert(TokenApi record) {
        return this.tokenApiMapper.insert(record);
    }

    @Override
    public int insertSelective(TokenApi record) {
        return this.tokenApiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(TokenApi record) {
        return this.tokenApiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(TokenApi record) {
        return this.tokenApiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.tokenApiMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TokenApi selectByPrimaryKey(Integer id) {
        return this.tokenApiMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TokenApi> selectAll(Map<Object, Object> param) {
        return this.tokenApiMapper.selectAll(param);
    }

    @Override
    public List<TokenApi> selectPaging(Map<Object, Object> param) {
        return this.tokenApiMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.tokenApiMapper.selectCount(param);
    }
}