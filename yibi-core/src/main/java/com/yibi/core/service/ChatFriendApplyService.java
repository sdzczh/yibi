package com.yibi.core.service;

import com.yibi.core.entity.ChatFriendApply;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:好友关系申请表 chat_friend_apply
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:09
 **/ 
public interface ChatFriendApplyService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(ChatFriendApply record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(ChatFriendApply record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(ChatFriendApply record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(ChatFriendApply record);

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
    ChatFriendApply selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatFriendApply> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<ChatFriendApply> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询好友申请关系
     * @param userId
     * @param friendId
     * @param state
     * @return
     */
    ChatFriendApply queryByUserAndFri(Integer userId, Integer friendId,int state);

    List queryApplyByUserId(Integer userId);
}