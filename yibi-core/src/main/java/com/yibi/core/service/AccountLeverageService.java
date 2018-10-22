package com.yibi.core.service;

import com.yibi.core.entity.AccountLeverage;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:杠杆账户 account_leverage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
public interface AccountLeverageService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insert(AccountLeverage record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insertSelective(AccountLeverage record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKey(AccountLeverage record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKeySelective(AccountLeverage record);

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
    AccountLeverage selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<AccountLeverage> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<AccountLeverage> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int selectCount(Map<Object, Object> param);
}