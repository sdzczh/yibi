package com.yibi.core.service;

import com.yibi.core.entity.Banner;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-18 14:49:35
 **/ 
public interface BannerService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    int insert(Banner record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    int insertSelective(Banner record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    int updateByPrimaryKey(Banner record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    int updateByPrimaryKeySelective(Banner record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    Banner selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    List<Banner> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    List<Banner> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 14:49:35
     **/ 
    int selectCount(Map<Object, Object> param);

    List<Banner> selectAllInfo(Map<Object, Object> param);

    /**
     * 根据类型查询有效的banner
     * @param type
     * @return
     */
    List<Banner> selectBannerByType(Integer type);
}