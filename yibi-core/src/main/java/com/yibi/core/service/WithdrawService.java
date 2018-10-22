package com.yibi.core.service;

import com.yibi.core.entity.Withdraw;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:提现记录 withdraw
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface WithdrawService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(Withdraw record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(Withdraw record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(Withdraw record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(Withdraw record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    Withdraw selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Withdraw> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Withdraw> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    Map<String,Object> queryDayWithdraw(Integer userId, String currentDateStr, Integer coinType);

    List<Withdraw> queryByUserIdAndType(Integer id, Integer pageInt, Integer rowsInt, Integer accountType, Integer coinType);

    /**
     * 后台通过条件显示提现订单内容
     * @param map
     * @return
     */
    List<Map<String,Object>> selectWithdrawOrPhone(Map<Object,Object> map);

    /**
     * 后台页面通过条件统计提现订单数量
     * @param map
     * @return
     */
    int selectCountWithdrawOrPhone(Map<Object,Object> map);
}