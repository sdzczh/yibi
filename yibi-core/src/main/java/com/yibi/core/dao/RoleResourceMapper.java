package com.yibi.core.dao;

import com.yibi.core.entity.RoleResource;
import java.util.List;
import java.util.Map;

public interface RoleResourceMapper {
    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    int updateByPrimaryKey(RoleResource record);

    int updateByPrimaryKeySelective(RoleResource record);

    int deleteByPrimaryKey(Integer id);

    RoleResource selectByPrimaryKey(Integer id);

    List<RoleResource> selectAll(Map<Object, Object> param);

    List<RoleResource> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}