package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.authorization.annotation.Sign;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录注册
* @author lina 
* @date 2017-12-26
* @version V1.0
 */
@Controller
@RequestMapping("/realname")
public class UserRealNameController extends BaseController{
	@Autowired
	private UserBiz userBiz;

	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="init",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String initRealName(@CurrentUser User user ){
		try {

			return userBiz.getToken(user);
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
	@RequestMapping(value="status",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String getStatus(@CurrentUser User user , @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String taskId = json.getString("taskId");
			/*参数校验*/
			if(StrUtils.isBlank(taskId)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//设置头像
			return userBiz.getStatus(user, taskId);
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
