package com.yibi.core.dao;

import com.yibi.core.entity.Doc;
import java.util.List;
import java.util.Map;

public interface DocMapper {
    int insert(Doc record);

    int insertSelective(Doc record);

    int updateByPrimaryKey(Doc record);

    int updateByPrimaryKeySelective(Doc record);

    int deleteByPrimaryKey(Integer id);

    Doc selectByPrimaryKey(Integer id);

    List<Doc> selectAll(Map<Object, Object> param);

    List<Doc> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}