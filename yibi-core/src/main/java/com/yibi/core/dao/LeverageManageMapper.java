package com.yibi.core.dao;

import com.yibi.core.entity.LeverageManage;
import java.util.List;
import java.util.Map;

public interface LeverageManageMapper {
    int insert(LeverageManage record);

    int insertSelective(LeverageManage record);

    int updateByPrimaryKey(LeverageManage record);

    int updateByPrimaryKeySelective(LeverageManage record);

    int deleteByPrimaryKey(Integer id);

    LeverageManage selectByPrimaryKey(Integer id);

    List<LeverageManage> selectAll(Map<Object, Object> param);

    List<LeverageManage> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}