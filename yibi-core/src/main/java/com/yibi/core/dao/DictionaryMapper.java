package com.yibi.core.dao;

import com.yibi.core.entity.Dictionary;
import java.util.List;
import java.util.Map;

public interface DictionaryMapper {
    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    int updateByPrimaryKeySelective(Dictionary record);

    int deleteByPrimaryKey(Integer id);

    Dictionary selectByPrimaryKey(Integer id);

    List<Dictionary> selectAll(Map<Object, Object> param);

    List<Dictionary> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}