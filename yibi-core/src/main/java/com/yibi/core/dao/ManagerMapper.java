package com.yibi.core.dao;

import com.yibi.core.entity.Manager;
import java.util.List;
import java.util.Map;

public interface ManagerMapper {
    int insert(Manager record);

    int insertSelective(Manager record);

    int updateByPrimaryKey(Manager record);

    int updateByPrimaryKeySelective(Manager record);

    int deleteByPrimaryKey(Integer id);

    Manager selectByPrimaryKey(Integer id);

    List<Manager> selectAll(Map<Object, Object> param);

    List<Manager> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}