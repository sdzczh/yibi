package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Sign;
import com.yibi.orderapi.biz.BankBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银行管理
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/bank")
public class BankController extends BaseController{
	@Autowired
	private BankBiz bankBiz;

	/**
	 * 查询开户行
	 * @param user
	 * @param params
	 * @return
	 * @return String
	 * @date 2018-2-27
	 * @author lina
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="list",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String list(@CurrentUser User user){
		try {
			//查询银行
			return bankBiz.queryList(0, 100);
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
}
