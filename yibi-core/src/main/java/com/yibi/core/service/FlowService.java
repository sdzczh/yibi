package com.yibi.core.service;

import com.yibi.core.entity.Flow;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:交易流水 flow
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface FlowService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(Flow record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(Flow record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(Flow record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(Flow record);

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
    Flow selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Flow> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Flow> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据用户和币种和账户查询流水
     *
     * @param userId
     * @param coinType
     * @return
     */
    List<Flow> queryByUserIdAndCoinTypeAndAccountType(Integer userId, Integer coinType, Integer accountType, Integer page, Integer rows);

    /**
     * 后台根据查询条件查询流水和姓名
     * @param map
     * @return
     */
    List<Map<String,Object>> selectFlowOrPhone(Map<Object,Object> map);

    /**
     * 后台根据查询条件统计记录数
     * @param map
     * @return
     */
    int selectFlowCount(Map<Object,Object> map);
}