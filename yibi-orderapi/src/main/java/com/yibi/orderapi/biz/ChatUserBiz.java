package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
public interface ChatUserBiz {
    /**
     * 搜索好友
     * @param user
     * @param account
     * @return
     * @return String
     * @date 2018-5-14
     * @author lina
     */
    String queryUserByAccount(User user, String account);

    /**
     *查看用户好友列表
     * @param user
     * @return String
     * @date 2018-5-20
     * @author lina
     */
    String queryList(User user);

    /**
     * 根据用户手机号查询详细信息
     * @param user
     * @param phone
     * @return String
     * @date 2018-5-15
     * @author lina
     */
    String getByPhone(User user, String phone);

    /**
     * 添加好友
     * @param user
     * @param phone
     * @return
     * @return String
     * @date 2018-5-15
     * @author lina
     */
    String addFriend(User user, String phone);


    /**
     * 获取好友申请列表
     * @param user
     * @return String
     * @date 2018-5-15
     * @author lina
     */
    String queryAddFriendApply(User user);

    /**
     * 审核好友申请
     * @param user
     * @param applyId
     * @param state
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    String checkFriendApply(User user, Integer applyId, Integer state);

    /**
     * 删除好友
     * @param user
     * @param phone
     * @return
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    String deleteFriend(User user, String phone);

    /**
     * 修改好友备注名
     * @param user
     * @param friendPhone
     * @param remarkName
     * @return String
     * @date 2018-5-20
     * @author lina
     */
    String updateRemarkName(User user, String friendPhone, String remarkName);
}
