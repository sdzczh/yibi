package com.yibi.core.service;

import com.yibi.core.entity.AboutInfo;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-18 16:54:11
 **/ 
public interface AboutInfoService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int insert(AboutInfo record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int insertSelective(AboutInfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int updateByPrimaryKey(AboutInfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int updateByPrimaryKeySelective(AboutInfo record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    AboutInfo selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    List<AboutInfo> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    List<AboutInfo> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int selectCount(Map<Object, Object> param);
}