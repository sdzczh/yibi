package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.ChatFriendApply;
import com.yibi.core.entity.ChatFriends;
import com.yibi.core.entity.User;
import com.yibi.core.service.ChatFriendApplyService;
import com.yibi.core.service.ChatFriendsService;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.rongcloud.request.ContactNotificationMessageData;
import com.yibi.extern.api.rongcloud.request.RongCloudMsgRequest;
import com.yibi.orderapi.biz.ChatUserBiz;
import com.yibi.orderapi.constants.ChatVariables;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import io.rong.messages.ContactNtfMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
@Service
@Transactional
public class ChatUserBizImpl implements ChatUserBiz {

    @Autowired
    private ChatFriendsService chatFriendsService;
    @Autowired
    private ChatFriendApplyService chatFriendApplyService;
    @Autowired
    private UserService userService;
    @Autowired
    private RongCloudMsgRequest msgRequest;
    @Override
    public String queryUserByAccount(User user, String account) {
        Map<String, Object> map = new HashMap  ();
        User queryUser = userService.selectByPhone(account);

        List<Object> list = new ArrayList<>();
        if(queryUser!=null){
            Map<String, Object> userMap = Maps.newHashMap();
            userMap.put("phone",queryUser.getPhone());
            userMap.put("headPath",queryUser.getHeadpath());
            userMap.put("nickName",queryUser.getNickname());
            list.add(userMap);
        }
        map.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String queryList(User user) {
        Map<String, Object> map = new HashMap();
        List<?> list = chatFriendsService.queryFriends(user.getId());
        map.put("list", list);

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String getByPhone(User user, String phone) {
        Map map = chatFriendsService.queryUserModelByPhone(phone, user.getId());
        if(map == null){
            return Result.toResult(ResultCode.USER_NOT_EXIST);
        }

        map.put("isFriend",(Long) map.get("isFriend")==1);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String addFriend(User user, String phone) {
        if(user.getPhone().equals(phone)){
            return Result.toResult(ResultCode.TAKL_FRIEND_NOT_SELF);
        }
        User friUser = userService.selectByPhone(phone);
		/*好友已经存在*/
        ChatFriends fri = chatFriendsService.getByUser(user.getId(), friUser.getId());
        if(fri!=null){
            return Result.toResult(ResultCode.TAKL_FRIEND_EXIST);
        }

		/*好友申请已经存在*/
        ChatFriendApply applyOld= chatFriendApplyService.queryByUserAndFri(user.getId(), friUser.getId(),GlobalParams.ORDER_STATE_UNTREATED);
        if(applyOld!=null ){
            return Result.toResult(ResultCode.TAKL_FRIEND_APPLY_EXIST);
        }

        ChatFriendApply apply = new ChatFriendApply();
        apply.setUserid(user.getId());
        apply.setFriendid(friUser.getId());
        apply.setState(GlobalParams.APPLY_STATE_WAITINT_PROCESS);

        chatFriendApplyService.insert(apply);

		/*推送消息*/
        ContactNotificationMessageData data = new ContactNotificationMessageData(user.getNickname(),System.currentTimeMillis());
        String extra = ((JSONObject) JSON.toJSON(data)).toJSONString();
        ContactNtfMessage msg = new ContactNtfMessage(ChatVariables.CONTACT_OPERATION_REQUEST, extra, user.getPhone(), phone, String.format(ChatVariables.CONTACT_OPERATION_REQUEST_TEMPLATE, user.getNickname()));
        try {
            msgRequest.sendSystemMassage(user.getPhone(), msg, phone);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String queryAddFriendApply(User user) {
        Map<String, Object> map = new HashMap();
        List<?> list = chatFriendApplyService.queryApplyByUserId(user.getId());
        map.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String checkFriendApply(User user, Integer applyId, Integer state) {
        ChatFriendApply apply = chatFriendApplyService.selectByPrimaryKey(applyId);
        if(apply==null || apply.getFriendid().intValue() !=user.getId().intValue()){
            return Result.toResult(ResultCode.TAKL_FRIEND_APPLY_NOTEXIST);
        }
        if(apply.getState() != GlobalParams.ORDER_STATE_UNTREATED){
            return Result.toResult(ResultCode.TAKL_FRIEND_APPLY_DEALED);
        }
        User applyUser = userService.selectByPrimaryKey(apply.getUserid());
        apply.setState(state);
        chatFriendApplyService.updateByPrimaryKey(apply);

        String msgOperation = "";//推送消息
        String msgTemplate = "";//推送消息模板

        if(state == GlobalParams.ORDER_STATE_TREATED){
            ChatFriends fri = chatFriendsService.getByUser(apply.getFriendid(), apply.getUserid());
            if(fri == null){
                fri = new ChatFriends();
                fri.setUserid(apply.getFriendid());
                fri.setFriendid(apply.getUserid());
                fri.setRemarkname("");
                chatFriendsService.insert(fri);
            }

            ChatFriends fri2 = chatFriendsService.getByUser(apply.getUserid(), apply.getFriendid());
            if(fri2 ==null){
                fri2 = new ChatFriends();
                fri2.setUserid(apply.getUserid());
                fri2.setFriendid(apply.getFriendid());
                fri2.setRemarkname("");
                chatFriendsService.insert(fri2);
            }

            msgOperation = ChatVariables.CONTACT_OPERATION_ACCEPT_RESPONSE;
            msgTemplate = String.format(ChatVariables.CONTACT_OPERATION_ACCEPT_RESPONSE_TEMPLATE, user.getNickname());
        }else{
            msgOperation = ChatVariables.CONTACT_OPERATION_REJECT_RESPONSE;
            msgTemplate = String.format(ChatVariables.CONTACT_OPERATION_REJECT_RESPONSE_TEMPLATE, user.getNickname());

        }

		/*推送消息*/
        ContactNotificationMessageData data = new ContactNotificationMessageData(user.getNickname(),System.currentTimeMillis());
        String extra = ((JSONObject) JSON.toJSON(data)).toJSONString();
        ContactNtfMessage msg = new ContactNtfMessage(msgOperation, extra, user.getPhone(), applyUser.getPhone(),msgTemplate );
        try {
            msgRequest.sendSystemMassage(user.getPhone(), msg, applyUser.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String deleteFriend(User user, String phone) {
        User friend = userService.selectByPhone(phone);
        if(friend == null ){
            Result.toResult(ResultCode.USER_NOT_EXIST);
        }
        int inx = chatFriendsService.deleteFriend(user.getId(), friend.getId());
        if(inx>0){
            return  Result.toResult(ResultCode.SUCCESS);
        }else{
            return Result.toResult(ResultCode.TAKL_FRIEND_NOT_EXIST);
        }
    }

    @Override
    public String updateRemarkName(User user, String friendPhone, String remarkName) {
        String rname = StrUtils.filterEmoji(remarkName);
        User friUser = userService.selectByPhone(friendPhone);
		/*好友不存在*/
        ChatFriends fri = chatFriendsService.getByUser(user.getId(), friUser.getId());
        if(fri==null){
            return Result.toResult(ResultCode.TAKL_FRIEND_NOT_EXIST);
        }

        fri.setRemarkname(rname);
        chatFriendsService.updateByPrimaryKey(fri);
        return Result.toResult(ResultCode.SUCCESS);
    }
}
