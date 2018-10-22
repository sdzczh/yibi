package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.authorization.annotation.Sign;
import com.yibi.orderapi.biz.ChatUserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

@RequestMapping("/talk/friends")
@Controller
public class ChatUserController {
    @Autowired
    private ChatUserBiz chatUserBiz;

    /**
     * 搜索好友
     * @param user
     * @return
     * @return String
     * @date 2018-5-14
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="find",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String getStatus(@CurrentUser User user , @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String account = json.getString("account");
			/*参数校验*/
            if(StrUtils.isBlank(account)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatUserBiz.queryUserByAccount(user,account);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="detail",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String getUserById(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String account = json.getString("phone");
			/*参数校验*/
            if(StrUtils.isBlank(account)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatUserBiz.getByPhone(user,account);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 添加好友
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="add",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String addFriend(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String account = json.getString("phone");
			/*参数校验*/
            if(StrUtils.isBlank(account)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatUserBiz.addFriend(user,account);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 好友我的好友列表
     * @param user
     * @param params
     * @return String
     * @date 2018-5-20
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryFriendList(@CurrentUser User user ,@Params Object params){
        try {

            return chatUserBiz.queryList(user);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 获取好友申请列表
     * @param user
     * @param params
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="addlist",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryAddList(@CurrentUser User user ,@Params Object params){
        try {

            return chatUserBiz.queryAddFriendApply(user);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 审核好友申请
     * @param user
     * @param params
     * @return
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="check",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String checkFriendApply(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer applyId = json.getInteger("applyId");
            Integer state = json.getInteger("state");
			/*参数校验*/
            if(applyId==null||state ==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatUserBiz.checkFriendApply(user, applyId, state);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }


    /**
     * 删除好友
     * @param user
     * @param params
     * @return
     * @return String
     * @date 2018-5-15
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="delete",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String deleteFriend(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String phone = json.getString("phone");
			/*参数校验*/
            if(StrUtils.isBlank(phone)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatUserBiz.deleteFriend(user, phone);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 修改备注名称
     * @param user
     * @param params
     * @return
     * @return String
     * @date 2018-5-20
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="/remarkname/update",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String updateRemarkName(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String friendPhone = json.getString("phone");
            String remarkName = json.getString("remarkName");
			/*参数校验*/
            if(StrUtils.isBlank(friendPhone)||StrUtils.isBlank(remarkName)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatUserBiz.updateRemarkName(user, friendPhone, remarkName);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
