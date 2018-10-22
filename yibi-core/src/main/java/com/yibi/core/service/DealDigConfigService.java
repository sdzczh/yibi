package com.yibi.core.service;

import com.yibi.core.entity.DealDigConfig;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:交易挖矿配置 deal_dig_config
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface DealDigConfigService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(DealDigConfig record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(DealDigConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(DealDigConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(DealDigConfig record);

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
    DealDigConfig selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<DealDigConfig> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<DealDigConfig> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 后台查询交易挖矿管理，关联币种条件
     * @return
     */
    List<Map<String ,Object>> selectConditionAll(Map<Object, Object> param);

    /**
     * 后台条件统计记录
     * @param param
     * @return
     */
    int selectConditionCount(Map<Object, Object> param);
}