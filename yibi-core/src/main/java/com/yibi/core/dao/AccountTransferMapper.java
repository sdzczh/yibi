package com.yibi.core.dao;

import com.yibi.core.entity.AccountTransfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountTransferMapper {
    int insert(AccountTransfer record);

    int insertSelective(AccountTransfer record);

    int updateByPrimaryKey(AccountTransfer record);

    int updateByPrimaryKeySelective(AccountTransfer record);

    int deleteByPrimaryKey(Integer id);

    AccountTransfer selectByPrimaryKey(Integer id);

    List<AccountTransfer> selectAll(Map<Object, Object> param);

    List<AccountTransfer> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    BigDecimal querySumByCoinAndTime(Map<Object, Object> param);
}