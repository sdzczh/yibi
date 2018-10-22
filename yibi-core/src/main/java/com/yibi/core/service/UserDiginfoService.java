package com.yibi.core.service;

import com.yibi.core.entity.UserDiginfo;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:用户挖矿信息 user_diginfo
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface UserDiginfoService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(UserDiginfo record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(UserDiginfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(UserDiginfo record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(UserDiginfo record);

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
    UserDiginfo selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<UserDiginfo> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<UserDiginfo> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    UserDiginfo saveOrUpdate(UserDiginfo diginfo);

    /**
     * 后台查询用户魂力以及用户手机号姓名等信息
     * @param map
     * @return
     */
    List<Map<String,Object>> selectDigInfoAndPhone(Map<Object,Object> map);

    /**
     * 后台页面通过查询条件统计人数
     * @param map
     * @return
     */
    int selectDigInfoCount(Map<Object,Object> map);

    UserDiginfo queryByUserId(Integer userId);

    int queryCountBySoulLevel(Integer soulMin,Integer soulMax);

    int queryRankByUserId(Integer userId);

    List<?> queryRankList(Integer page,Integer rows);

    /**
     * 查询签到中断的用户魂力信息
     * @param page
     * @param rows
     * @return
     */
    List<UserDiginfo> querySignInterList(Integer page,Integer rows);

    /**
     * 批量修改用户的魂力值
     * @param userIds
     * @param calculForce
     * @return
     */
    int updateCalculForceBatch(List<Integer> userIds,Integer calculForce);


}