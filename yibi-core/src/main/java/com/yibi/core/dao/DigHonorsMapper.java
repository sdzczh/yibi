package com.yibi.core.dao;

import com.yibi.core.entity.DigHonors;
import java.util.List;
import java.util.Map;

public interface DigHonorsMapper {
    int insert(DigHonors record);

    int insertSelective(DigHonors record);

    int updateByPrimaryKey(DigHonors record);

    int updateByPrimaryKeySelective(DigHonors record);

    int deleteByPrimaryKey(Integer id);

    DigHonors selectByPrimaryKey(Integer id);

    List<DigHonors> selectAll(Map<Object, Object> param);

    List<DigHonors> queryBySoul(Map<Object, Object> param);

    List<DigHonors> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    DigHonors selectByCalcul(Integer calcul);
}