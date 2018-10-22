package com.yibi.core.dao;

import com.yibi.core.entity.AccountChain;
import java.util.List;
import java.util.Map;

public interface AccountChainMapper {
    int insert(AccountChain record);

    int insertSelective(AccountChain record);

    int updateByPrimaryKey(AccountChain record);

    int updateByPrimaryKeySelective(AccountChain record);

    int deleteByPrimaryKey(Integer id);

    AccountChain selectByPrimaryKey(Integer id);

    List<AccountChain> selectAll(Map<Object, Object> param);

    List<AccountChain> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    int updateSize(AccountChain acc);
}