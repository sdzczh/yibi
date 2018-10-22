package com.yibi.core.service;

import com.yibi.core.entity.CoinScale;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:币种小数位数及最小值管理 coin_scale
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface CoinScaleService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(CoinScale record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(CoinScale record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(CoinScale record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(CoinScale record);

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
    CoinScale selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<CoinScale> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<CoinScale> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    CoinScale queryByCoin(Integer orderCoinType,Integer unitCoinType);

    /**
     * 条件查询币种小数位数
     * @param param
     * @return
     */
    List<Map<String,Object>> selectConditionPaging(Map<Object, Object> param);

    /**
     * 条件统计记录条数
     * @param param
     * @return
     */
    int selectConditionCount(Map<Object, Object> param);
}