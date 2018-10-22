package com.yibi.core.dao;

import com.yibi.core.entity.Account;
import java.util.List;
import java.util.Map;

public interface AccountMapper {
    int insert(Account record);

    int insertSelective(Account record);

    int updateByPrimaryKey(Account record);

    int updateByPrimaryKeySelective(Account record);

    int deleteByPrimaryKey(Integer id);

    Account selectByPrimaryKey(Integer id);

    List<Account> selectAll(Map<Object, Object> param);

    List<Account> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    int updateBalance(Account record);

    List<Account> selectAllForWallet(Map<Object, Object> param);

    List<Map<String,Object>> selectAccountOrPhone(Map<Object,Object> map);

    int selectAccountCount(Map<Object,Object> map);

    List<Account> queryByAvailBalance(Map<Object,Object> map);

    List<Account> selectBySeque(Map map);
}