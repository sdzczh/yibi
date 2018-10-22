package com.yibi.core.service;

import com.yibi.core.entity.Sysparams;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:系统参数 sysparams
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface SysparamsService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(Sysparams record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(Sysparams record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(Sysparams record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(Sysparams record);

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
    Sysparams selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Sysparams> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Sysparams> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据KEY查询
     * @param key
     * @return
     */
    Sysparams getValByKey(String key);

    String getValStringByKey(String key);

    /**
     * 通过条件查询系统配置
     * @param remarks
     * @return
     */
    List<Map<String,Object>> selectSystemParamByCondition(Map<Object,Object> map);

    int selectSystemParamCountByCondition(Map<Object,Object> map);

    int getValIntByKey(String key);

}