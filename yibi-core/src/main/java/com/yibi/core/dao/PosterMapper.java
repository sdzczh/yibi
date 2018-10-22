package com.yibi.core.dao;

import com.yibi.core.entity.Poster;
import java.util.List;
import java.util.Map;

public interface PosterMapper {
    int insert(Poster record);

    int insertSelective(Poster record);

    int updateByPrimaryKey(Poster record);

    int updateByPrimaryKeySelective(Poster record);

    int deleteByPrimaryKey(Integer id);

    Poster selectByPrimaryKey(Integer id);

    List<Poster> selectAll(Map<Object, Object> param);

    List<Poster> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}