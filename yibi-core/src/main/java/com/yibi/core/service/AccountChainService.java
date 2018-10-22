package com.yibi.core.service;

import com.yibi.core.entity.AccountChain;
import com.yibi.core.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
public interface AccountChainService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insert(AccountChain record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insertSelective(AccountChain record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKey(AccountChain record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKeySelective(AccountChain record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    AccountChain selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<AccountChain> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<AccountChain> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int selectCount(Map<Object, Object> param);

    List<AccountChain> getChainList(Integer type, int page, int rows);

    AccountChain changeSize(User user, Integer type);


}