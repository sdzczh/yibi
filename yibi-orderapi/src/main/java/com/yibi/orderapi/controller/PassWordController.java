package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Decrypt;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.biz.PassWordBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 密码管理
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/pwd")
public class PassWordController extends BaseController{
	@Autowired
	private PassWordBiz passWordBiz;

	/**
	 * 用户修改登录密码
	 * @param params
	 * @return
	 */

	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="login/update",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String update(@CurrentUser User user , @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String password = json.getString("password");
			String code = json.getString("code");
			Integer codeId = json.getInteger("codeId");

			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(code)||codeId==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isDigitalAndWord(password)){
				return Result.toResult(ResultCode.PASSWORD_TYPE_ERROR);
			}
			if(!ValidateUtils.isVerificationCode(code)){
				return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
			}
			//找回密码
			return passWordBiz.update(user, password,code,codeId);
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
	}/**
	 * 用户找回登录密码
	 * @param params
	 * @return
	 */

	@Decrypt
	@ResponseBody
	@RequestMapping(value="login/getback",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getback(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String phone = json.getString("phone");
			String password = json.getString("password");
			String code = json.getString("code");
			Integer codeId = json.getInteger("codeId");

			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(code)||codeId==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isDigitalAndWord(password)){
				return Result.toResult(ResultCode.PASSWORD_TYPE_ERROR);
			}
			if(!ValidateUtils.isVerificationCode(code)){
				return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
			}
			//找回密码
			return passWordBiz.getback(phone, password,code,codeId);
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
	 * 设置交易密码
	 * @param user
	 * @param params
     * @return
     */
	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="order/set",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String set(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String password = json.getString("password");
			String code = json.getString("code");
			Integer codeId = json.getInteger("codeId");

			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(code)||codeId==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isVerificationCode(code)){
				return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
			}
			//找回密码
			return passWordBiz.setOrderPwd(user, password,code,codeId);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		} catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}

	}

	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="order/update",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String updateOrderPwd(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String password = json.getString("password");
			String oldPassword = json.getString("oldPassword");
			String code = json.getString("code");
			Integer codeId = json.getInteger("codeId");

			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(code)||codeId==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isVerificationCode(code)){
				return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
			}
			return passWordBiz.updateOrderPwd(user, password,code,codeId);
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
