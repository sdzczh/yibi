package com.yibi.core.service;

import com.yibi.core.entity.CommissionInvite;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:邀请奖励表 commission_invite
 * 
 * @author: autogeneration
 * @date: 2018-07-25 09:26:09
 **/ 
public interface CommissionInviteService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    int insert(CommissionInvite record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    int insertSelective(CommissionInvite record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    int updateByPrimaryKey(CommissionInvite record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    int updateByPrimaryKeySelective(CommissionInvite record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    CommissionInvite selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    List<CommissionInvite> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    List<CommissionInvite> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-25 09:26:09
     **/ 
    int selectCount(Map<Object, Object> param);
}