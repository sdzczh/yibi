package com.yibi.core.dao;

import com.yibi.core.entity.BindInfo;
import java.util.List;
import java.util.Map;

public interface BindInfoMapper {
    int insert(BindInfo record);

    int insertSelective(BindInfo record);

    int updateByPrimaryKey(BindInfo record);

    int updateByPrimaryKeySelective(BindInfo record);

    int deleteByPrimaryKey(Integer id);

    BindInfo selectByPrimaryKey(Integer id);

    List<BindInfo> selectAll(Map<Object, Object> param);

    List<BindInfo> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}