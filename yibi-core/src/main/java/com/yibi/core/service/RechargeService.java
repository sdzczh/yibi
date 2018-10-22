package com.yibi.core.service;

import com.yibi.core.entity.Recharge;
import com.yibi.core.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:充值记录 recharge
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface RechargeService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(Recharge record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(Recharge record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(Recharge record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(Recharge record);

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
    Recharge selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Recharge> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Recharge> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据txid查询充值记录
     * @param txid
     * @return
     */
    Recharge getRechargeByTxid(String txid);

    /**
     * 后台通过查询条件获取充值订单
     * @param map
     * @return
     */
    List<Map<String,Object>> selectRechargeOrPhone(Map<Object,Object> map );

    /**
     * 后台通过条件统计充值订单
     * @param map
     * @return
     */
    int selectCountRechargeOrPhone (Map<Object,Object> map);

    /**
     * 添加充值记录
     * @param user
     * @param amount
     * @param coinType
     * @param txid
     * @return
     */
    Recharge rechargeApply(User user, BigDecimal amount, Integer coinType, String txid);
}