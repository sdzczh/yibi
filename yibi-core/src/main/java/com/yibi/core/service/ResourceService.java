package com.yibi.core.service;

import com.yibi.core.entity.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:资源 resource
 * 
 * @author: autogeneration
 * @date: 2018-07-10 15:12:25
 **/ 
public interface ResourceService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int insert(Resource record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int insertSelective(Resource record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int updateByPrimaryKey(Resource record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int updateByPrimaryKeySelective(Resource record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    Resource selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    List<Resource> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    List<Resource> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询用户所有可用资源
     * @param params
     * @return
     */
    List<Resource> selectRescourcesByManager(Map<String, Object> params);

    /**
     * 获取主页上的信息
     * @return
     */
    Map<String,Object> getIndexInfo();
}