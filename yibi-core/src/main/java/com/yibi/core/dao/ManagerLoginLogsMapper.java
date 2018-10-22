package com.yibi.core.dao;

import com.yibi.core.entity.ManagerLoginLogs;
import java.util.List;
import java.util.Map;

public interface ManagerLoginLogsMapper {
    int insert(ManagerLoginLogs record);

    int insertSelective(ManagerLoginLogs record);

    int updateByPrimaryKey(ManagerLoginLogs record);

    int updateByPrimaryKeySelective(ManagerLoginLogs record);

    int deleteByPrimaryKey(Integer id);

    ManagerLoginLogs selectByPrimaryKey(Integer id);

    List<ManagerLoginLogs> selectAll(Map<Object, Object> param);

    List<ManagerLoginLogs> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}