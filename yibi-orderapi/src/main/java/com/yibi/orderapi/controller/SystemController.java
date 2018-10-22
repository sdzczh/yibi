package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.biz.SystemBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统设置
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController{
	@Autowired
	private SystemBiz systemBiz;

	@ResponseBody
	@RequestMapping(value="update",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String checkUpdate(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			//手机类型
			Integer syetemType = json.getInteger("systemType");
			Double version = json.getDouble("version");
			/*参数校验*/
			if(syetemType==null||version==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			/*if(ck.checkType2(syetemType)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}*/

			int verInt = (int) Math.ceil(version);
			//获取版本信息
			return systemBiz.checkUpdate(syetemType,verInt);

		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value="aboutInfo",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String aboutInfo(){
		try {

			return systemBiz.aboutInfo();

		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}
	@ResponseBody
	@RequestMapping(value="config",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String getStartupParam2(@Params Object params){
		try {

			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer versionCode = json.getInteger("versionCode");


			//获取启动参数
			return systemBiz.getStartupParam(versionCode);
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}

	/**
	 * 海报信息
	 * @param user
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="poster",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String getPoster(@CurrentUser User user){
		try {
			return systemBiz.getPoster(user);
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
