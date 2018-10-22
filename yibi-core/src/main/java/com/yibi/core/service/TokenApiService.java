package com.yibi.core.service;

import com.yibi.core.entity.TokenApi;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-08-14 17:20:32
 **/ 
public interface TokenApiService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    int insert(TokenApi record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    int insertSelective(TokenApi record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    int updateByPrimaryKey(TokenApi record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    int updateByPrimaryKeySelective(TokenApi record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    TokenApi selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    List<TokenApi> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    List<TokenApi> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-08-14 17:20:32
     **/ 
    int selectCount(Map<Object, Object> param);
}