package com.yibi.core.dao;

import com.yibi.core.entity.Manager;
import com.yibi.core.entity.Withdraw;
import java.util.List;
import java.util.Map;

public interface WithdrawMapper {
    int insert(Withdraw record);

    int insertSelective(Withdraw record);

    int updateByPrimaryKey(Withdraw record);

    int updateByPrimaryKeySelective(Withdraw record);

    int deleteByPrimaryKey(Integer id);

    Withdraw selectByPrimaryKey(Integer id);

    List<Withdraw> selectAll(Map<Object, Object> param);

    List<Withdraw> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<?> queryDayWithdraw(Map<Object, Object> map);

    List<Map<String,Object>> selectWithdrawOrPhone(Map<Object,Object> map);

    int selectCountWithdrawOrPhone(Map<Object,Object> map);
}