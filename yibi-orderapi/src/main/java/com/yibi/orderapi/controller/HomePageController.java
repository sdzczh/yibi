package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.biz.BannerBiz;
import com.yibi.orderapi.biz.HomePageBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/homePage")
public class HomePageController extends BaseController{
	@Autowired
	private HomePageBiz homePageBiz;
	@Autowired
	private BannerBiz bannerBiz;

	/**
	 * 未登录首页
	 * @return
     */
	@ResponseBody
	@RequestMapping(value="out/init",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String outIndex(){
		try {
			return homePageBiz.initOut();

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

	/**
	 * 已登录首页
	 * @param user
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="in/init",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String inIndex(@CurrentUser User user){
		try {
			return homePageBiz.initIn(user);

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
	@RequestMapping(value="banner",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String banner(@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer bannerType = json.getInteger("bannerType");
			//参数校验
			if(bannerType == null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}

			return bannerBiz.getBannerByType(bannerType);

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
}
