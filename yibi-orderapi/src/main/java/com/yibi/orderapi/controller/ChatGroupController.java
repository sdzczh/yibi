package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.authorization.annotation.Sign;
import com.yibi.orderapi.biz.ChatGroupBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

@RequestMapping("/talk/group")
@Controller
public class ChatGroupController {
    @Resource
    private ChatGroupBiz chatGroupBiz;

    /**
     * 查询群组列表
     * @param user
     * @return String
     * @date 2018-5-14
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="list",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String getStatus(@CurrentUser User user , @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer type = json.getInteger("type");
			/*参数校验*/
            if(type ==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatGroupBiz.queryList(type);
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
    public String queryGroupInfo(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String groupId = json.getString("groupId");
			/*参数校验*/
            if(StringUtils.isBlank(groupId)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatGroupBiz.queryGroupInfo(user,groupId);
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
     * 查询群组成员列表
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
    @RequestMapping(value="users",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String getGroupUsers(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String groupId = json.getString("groupId");
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
			/*参数校验*/
            if(groupId ==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatGroupBiz.queryUsers(user, groupId, page, rows);
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
     * 加入群组
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
    @RequestMapping(value="join",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String joinGroup(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String groupId = json.getString("groupId");
            Integer type = json.getInteger("type");
			/*参数校验*/
            if(groupId ==null || type == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatGroupBiz.joinGroup(user, groupId, type);
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
     * 离开群组
     * @param user
     * @param params
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="leave",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String leaveGroup(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String groupId = json.getString("groupId");
			/*参数校验*/
            if(groupId ==null ){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatGroupBiz.leaveGroup(user, groupId);
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
     * 设置消息免打扰
     * @param user
     * @param params
     * @return
     * @return String
     * @date 2018-6-8
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="mute",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String mute(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String groupId = json.getString("groupId");
			/*参数校验*/
            if(groupId ==null ){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatGroupBiz.mute(user, groupId);
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
