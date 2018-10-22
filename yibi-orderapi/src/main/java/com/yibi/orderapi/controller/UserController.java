package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 我的信息
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private UserBiz userBiz;

	/**
	 * 设置头像
	 * @param user
	 * @param params
	 * @return String
	 * @date 2018-7-16
	 * @author zhaohe
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="headimg",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String setHeadimg(@CurrentUser User user , @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String imgPath = json.getString("imgPath");
			/*参数校验*/
			if(StrUtils.isBlank(imgPath)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//设置头像
			return userBiz.setHeadimg(user,imgPath);
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
	 * 设置昵称
	 * @param user
	 * @param params
	 * @return String
	 * @date 2018-7-16
	 * @author zhaohe
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="nickname/update",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String setNickname(@CurrentUser User user ,@Params Object params) {
		try {
			if (params == null || !(params instanceof JSONObject)) {
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject) params;
			String nickname = json.getString("nickname");
			/*参数校验*/
			if (StrUtils.isBlank(nickname)) {
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//设置昵称
			return userBiz.setNickname(user, nickname);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		} catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}

	/**
	 * 绑定支付宝
	 * @param user
	 * @param params
     * @return
     */
	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="bindalipay",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String bindalipayNew(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String password = json.getString("password");
			String name = json.getString("name");
			String alipayNum = json.getString("account");
			String imgUrl = json.getString("imgUrl");
			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(alipayNum)||StrUtils.isBlank(name)||StrUtils.isBlank(imgUrl)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isTradePwd(password)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}
			//绑定支付宝
			return userBiz.bindInfo(user, password, alipayNum, name, imgUrl, "", "", GlobalParams.PAY_ALIPAY);
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
	 * 绑定微信
	 * @param user
	 * @param params
     * @return
     */
	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="bindwechat",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String bindwebcat(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String password = json.getString("password");
			String name = json.getString("name");
			String account = json.getString("account");
			String imgUrl = json.getString("imgUrl");
			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(account)|| StrUtils.isBlank(imgUrl) || StrUtils.isBlank(name)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isTradePwd(password)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}
			//绑定微信
			return userBiz.bindInfo(user, password, account, name, imgUrl, "", "", GlobalParams.PAY_WECHANT);
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
	 * 绑定银行卡
	 * @param user
	 * @param params
     * @return
     */
	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="bindcard",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String bindcard(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String password = json.getString("password");
			String account = json.getString("account");
			String name = json.getString("name");
			String bankName = json.getString("bankName");
			String branchName = json.getString("branchName");
			/*参数校验*/
			if(StrUtils.isBlank(password)||StrUtils.isBlank(account)|| StrUtils.isBlank(name)|| StrUtils.isBlank(bankName)|| StrUtils.isBlank(branchName)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isTradePwd(password)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}
			//绑定银行卡
			return userBiz.bindInfo(user, password, account, name, "", bankName, branchName, GlobalParams.PAY_BANK);
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
	 * 用户退出登录
	 * @param params
	 * @return
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="exit",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String exit(@CurrentUser User user){
		try {
			return Result.toResult(userBiz.exit(user));
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
	}	/**
	 * 用户获取魂力-称号
	 * @param params
	 * @return
	 */
	@Authorization
	@ResponseBody
	@RequestMapping(value="getCalcul",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getCalcul(@CurrentUser User user){
		try {
			return userBiz.getCalcul(user);
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
