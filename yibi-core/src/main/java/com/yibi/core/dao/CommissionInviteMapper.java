package com.yibi.core.dao;

import com.yibi.core.entity.CommissionInvite;
import java.util.List;
import java.util.Map;

public interface CommissionInviteMapper {
    int insert(CommissionInvite record);

    int insertSelective(CommissionInvite record);

    int updateByPrimaryKey(CommissionInvite record);

    int updateByPrimaryKeySelective(CommissionInvite record);

    int deleteByPrimaryKey(Integer id);

    CommissionInvite selectByPrimaryKey(Integer id);

    List<CommissionInvite> selectAll(Map<Object, Object> param);

    List<CommissionInvite> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}