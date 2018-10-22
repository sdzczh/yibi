package com.yibi.core.dao;

import com.yibi.core.entity.AccountLeverage;
import java.util.List;
import java.util.Map;

public interface AccountLeverageMapper {
    int insert(AccountLeverage record);

    int insertSelective(AccountLeverage record);

    int updateByPrimaryKey(AccountLeverage record);

    int updateByPrimaryKeySelective(AccountLeverage record);

    int deleteByPrimaryKey(Integer id);

    AccountLeverage selectByPrimaryKey(Integer id);

    List<AccountLeverage> selectAll(Map<Object, Object> param);

    List<AccountLeverage> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}