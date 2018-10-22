package com.yibi.core.dao;

import com.yibi.core.entity.Role;
import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    int updateByPrimaryKey(Role record);

    int updateByPrimaryKeySelective(Role record);

    int deleteByPrimaryKey(Integer id);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll(Map<Object, Object> param);

    List<Role> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);


}