package com.yibi.core.service;

import com.yibi.core.entity.BindInfo;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
public interface BindInfoService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insert(BindInfo record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int insertSelective(BindInfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKey(BindInfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int updateByPrimaryKeySelective(BindInfo record);

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
    BindInfo selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<BindInfo> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    List<BindInfo> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:09
     **/ 
    int selectCount(Map<Object, Object> param);

    List<BindInfo> queryByUser(Integer userId);

    BindInfo queryByUserAndType(Integer userId, Integer type);

    /**
     * 更新、插入
     * @param bind
     * @return
     */
    int updateOrInsertBindInfo(BindInfo bind);
}