package com.yibi.core.service;

import com.yibi.core.entity.YubiProfit;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:余币宝收益记录 yubi_profit
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface YubiProfitService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(YubiProfit record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(YubiProfit record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(YubiProfit record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(YubiProfit record);

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
    YubiProfit selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<YubiProfit> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<YubiProfit> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 后台管理条件查询余币包记录
     * @param map
     * @return
     */
    List<Map<String,Object>> selectYbProfitByCondition(Map<Object,Object> map);

    /**
     * 根据条件统计记录数
     * @param map
     * @return
     */
    int selectYbProfitByConditionCount(Map<Object,Object> map);

    /**
     * 查询执行币种的最后一次收益
     * @param userId
     * @param coinType
     * @return
     */
    YubiProfit queryLastProfit(Integer userId,Integer coinType);
}