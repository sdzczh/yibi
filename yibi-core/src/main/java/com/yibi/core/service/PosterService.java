package com.yibi.core.service;

import com.yibi.core.entity.Poster;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-25 14:23:40
 **/ 
public interface PosterService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    int insert(Poster record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    int insertSelective(Poster record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    int updateByPrimaryKey(Poster record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    int updateByPrimaryKeySelective(Poster record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    Poster selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    List<Poster> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    List<Poster> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 14:23:40
     **/ 
    int selectCount(Map<Object, Object> param);
}