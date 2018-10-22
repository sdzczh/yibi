package com.yibi.core.dao;

import com.yibi.core.entity.ManagerRole;
import java.util.List;
import java.util.Map;

public interface ManagerRoleMapper {
    int insert(ManagerRole record);

    int insertSelective(ManagerRole record);

    int updateByPrimaryKey(ManagerRole record);

    int updateByPrimaryKeySelective(ManagerRole record);

    int deleteByPrimaryKey(Integer id);

    ManagerRole selectByPrimaryKey(Integer id);

    List<ManagerRole> selectAll(Map<Object, Object> param);

    List<ManagerRole> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Integer> findRoleIdListByManagerId(Integer managerId);
}