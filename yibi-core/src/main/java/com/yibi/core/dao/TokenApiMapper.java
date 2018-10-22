package com.yibi.core.dao;

import com.yibi.core.entity.TokenApi;
import java.util.List;
import java.util.Map;

public interface TokenApiMapper {
    int insert(TokenApi record);

    int insertSelective(TokenApi record);

    int updateByPrimaryKey(TokenApi record);

    int updateByPrimaryKeySelective(TokenApi record);

    int deleteByPrimaryKey(Integer id);

    TokenApi selectByPrimaryKey(Integer id);

    List<TokenApi> selectAll(Map<Object, Object> param);

    List<TokenApi> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}