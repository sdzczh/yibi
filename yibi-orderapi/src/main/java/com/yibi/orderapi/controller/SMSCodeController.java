package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.biz.SmsCodeBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信验证码
* @author zhaohe
* @date 2018-7-12
* @version V1.0
 */
@Controller
@RequestMapping("/sms")
public class SMSCodeController extends BaseController{
	@Autowired
	SmsCodeBiz smsCodeBiz;

	/**
	 * @Description: 获取短信验证验证码
	 * @param  params 参数列表
	 * @return String
	 * @date 2017-8-8
	 * @author lina
	 */
	@ResponseBody
	@RequestMapping(value="smscode",method=RequestMethod.POST ,produces = "application/json;charset=utf-8")
	public String getValidateCode(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String phone = json.getString("phone");
			Integer type = json.getInteger("type");
			//参数校验
			if(StrUtils.isBlank(phone)||type==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isPhone(phone)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}
			//获取校验码
			String returnStr = smsCodeBiz.getValidateCode(phone, type);
			return returnStr;
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}

	}
}
