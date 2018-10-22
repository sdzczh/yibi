package com.yibi.core.service;

import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.CoinManageModel;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:币种管理 coin_manage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface CoinManageService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(CoinManage record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(CoinManage record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(CoinManage record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(CoinManage record);

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
    CoinManage selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<CoinManage> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<CoinManage> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    CoinManage queryByCoinType(Integer coinType);

    /**
     * 配置文件中返回币种信息
     * @param map
     * @return
     */
    List<CoinManageModel> queryAllByConfig(Map map);
}