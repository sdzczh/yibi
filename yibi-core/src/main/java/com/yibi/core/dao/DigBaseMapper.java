package com.yibi.core.dao;

import com.yibi.core.entity.DigBase;
import java.util.List;
import java.util.Map;

public interface DigBaseMapper {
    int insert(DigBase record);

    int insertSelective(DigBase record);

    int updateByPrimaryKey(DigBase record);

    int updateByPrimaryKeySelective(DigBase record);

    int deleteByPrimaryKey(Integer id);

    DigBase selectByPrimaryKey(Integer id);

    List<DigBase> selectAll(Map<Object, Object> param);

    List<DigBase> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
    List<Map<String,Object>> selectAllByCondtion();
}