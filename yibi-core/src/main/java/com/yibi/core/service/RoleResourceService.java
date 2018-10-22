package com.yibi.core.service;

import com.yibi.core.entity.RoleResource;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:角色资源关联表 role_resource
 * 
 * @author: autogeneration
 * @date: 2018-07-10 15:12:25
 **/ 
public interface RoleResourceService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int insert(RoleResource record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int insertSelective(RoleResource record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int updateByPrimaryKey(RoleResource record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int updateByPrimaryKeySelective(RoleResource record);

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
    RoleResource selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    List<RoleResource> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    List<RoleResource> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-10 15:12:25
     **/ 
    int selectCount(Map<Object, Object> param);
}