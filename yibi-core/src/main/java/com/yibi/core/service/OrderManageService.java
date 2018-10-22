package com.yibi.core.service;

import com.yibi.core.entity.OrderManage;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:交易管理 order_manage
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface OrderManageService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(OrderManage record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(OrderManage record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(OrderManage record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(OrderManage record);

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
    OrderManage selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderManage> selectAll(Map<Object, Object> param);

    List<OrderManage> selectAllOrderBySeque(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<OrderManage> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 条件查询
     * @param param
     * @return
     */
    List<Map<String,Object>> selectConditionPaging(Map<Object, Object> param);

    /**
     * 条件统计
     * @param param
     * @return
     */
    int selectConditionCount(Map<Object, Object> param);
}