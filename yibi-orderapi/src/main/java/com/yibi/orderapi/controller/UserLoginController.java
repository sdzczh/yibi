package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.PatternUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.orderapi.authorization.annotation.Decrypt;
import com.yibi.orderapi.authorization.annotation.Params;
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
@RequestMapping("/user")
public class UserLoginController extends BaseController{
	@Autowired
	private UserBiz userBiz;
	/**
	 * 用户注册
	 * @return String
	 * @date 2017-12-26
	 * @author lina
	 */
	@Decrypt
	@ResponseBody
	@RequestMapping(value="register",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String register(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			//手机号
			String phone = json.getString("phone");
			//验证码
			String code = json.getString("code");
			//验证码id
			Integer codeId = json.getInteger("codeId");
			//密码
			String password = json.getString("password");
			//推荐人手机号
			String referPhone = json.getString("referPhone");
			//设备号
			String deviceNum = json.getString("deviceNum");
			//手机类型
			Integer syetemType = json.getInteger("systemType");
			
			/*参数校验*/
			if(StrUtils.isBlank(phone)||StrUtils.isBlank(code)||codeId==null||StrUtils.isBlank(password)||syetemType==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!PatternUtil.isPhone(phone)){
				return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
			}
			if(!PatternUtil.isPhone(referPhone)){
				return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
			}
			if(!PatternUtil.isVerificationCode(code)){
				return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
			}
			if(!PatternUtil.isDigitalAndWord(password)){
				return Result.toResult(ResultCode.PASSWORD_TYPE_ERROR);
			}
			//获取校验码
			return userBiz.register(phone, code,codeId,password,referPhone,deviceNum,syetemType);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
		
	}

	@Decrypt
	@ResponseBody
	@RequestMapping(value="login",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String login(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String phone = json.getString("phone");
			String password = json.getString("password");
			String deviceNum = json.getString("deviceNum");
			Integer syetemType = json.getInteger("systemType");
			String secretKey = json.getString("secretKey");
			
			/*参数校验*/
			if(StrUtils.isBlank(phone)||StrUtils.isBlank(password)||StrUtils.isBlank(secretKey)||syetemType==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!PatternUtil.isPhone(phone)){
				return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
			}
			if(!PatternUtil.isDigitalAndWord(password)){
				return Result.toResult(ResultCode.PASSWORD_TYPE_ERROR);
			}
			//获取校验码
			return userBiz.login(phone, password,deviceNum,syetemType,secretKey);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
		
	}

/**
	 * 手机验证码登录
	 * @param params
	 * @return String
	 * @date 2018-4-19
	 * @author lina
	 */
	@Decrypt
	@ResponseBody
	@RequestMapping(value="login/phone",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String loginByPhone(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String phone = json.getString("phone");
			String code = json.getString("code");
			Integer codeId = json.getInteger("codeId");
			String deviceNum = json.getString("deviceNum");
			Integer systemType = json.getInteger("systemType");
			String secretKey = json.getString("secretKey");
			
			/*参数校验*/
			if(StrUtils.isBlank(phone)||StrUtils.isBlank(code)||StrUtils.isBlank(secretKey)||systemType==null||codeId==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!PatternUtil.isPhone(phone)){
				return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
			}
			//手机验证码登录
			return userBiz.loginByPhone(phone, code,codeId,deviceNum,systemType,secretKey);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
		
	}

}
