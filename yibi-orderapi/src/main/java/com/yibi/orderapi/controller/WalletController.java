package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.core.service.CoinScaleService;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.WalletBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 钱包
* @author zhaohe
* @date 2018-7-12
* @version V1.0
 */
@Controller
@RequestMapping("/wallets")
public class WalletController extends BaseController{
	@Autowired
	WalletBiz walletBiz;
	@Autowired
	CoinScaleService coinScaleService;

	/**
	 * 账户列表
	 * @param user
	 * @param params  accountType 账户类型
     * @return
     */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String wechatRechargeSpot(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer accountType = json.getInteger("accountType");
			/*参数校验*/
			if(accountType == null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			return walletBiz.queryByUser(user, accountType);
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
	@RequestMapping(value="availBalance",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String queryCoinAvailBalance(@CurrentUser User user,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			Integer accountType = json.getInteger("accountType");

			/*参数校验*/
			if(coinType==null||accountType==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}

			return walletBiz.queryCoinAvailBalance(user,coinType,accountType);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}

	/**
	 * 查询指定账户、币种的可用余额
	 * @param user
	 * @param params
	 * @return String
	 * @date 2018-2-10
	 * @author lina
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="accountDetails",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String accountDetails(@CurrentUser User user,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			Integer accountType = json.getInteger("accountType");
			Integer page = json.getInteger("page");
			Integer rows = json.getInteger("rows");

			/*参数校验*/
			if(coinType==null||accountType==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}

			return walletBiz.accountDetails(user,coinType,accountType, page, rows);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}

	/**
	 * 获取充值信息
	 * @param user
	 * @param params
     * @return
     */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="recharge/info",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String rechargeInfo(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			/*参数校验*/
			if(coinType==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//获取信息
			return walletBiz.rechargeInfo(user, coinType);
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
	 * 提现提交
	 * @param user
	 * @param params
     * @return
     */
	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="withDraw/apply",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String withDrawApply(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			Integer type = json.getInteger("type");
			String amount = json.getString("amount");
			String password = json.getString("password");
			String accountNum = json.getString("accountNum");

			/*参数校验*/

			if(type==null||coinType==null|| StrUtils.isBlank(amount)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isTradePwd(password)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}
			BigDecimal amountDec = BigDecimalUtils.roundDown(new BigDecimal(amount), coinScaleService.queryByCoin(coinType, -1).getCalculscale());
			//提现
			return walletBiz.withDrawApply(user, password, amountDec,type,accountNum,coinType);
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
	 * 提现列表
	 * @param user
	 * @param params
     * @return
     */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="withDraw/list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String withdrawQuery2(@CurrentUser User user ,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer page = json.getInteger("page");
			Integer rows = json.getInteger("rows");
			Integer accountType = json.getInteger("accountType");
			Integer coinType = json.getInteger("coinType");
			if(accountType==null||coinType==null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}

			//提现
			return walletBiz.withDrawQueryAll(user,page,rows,accountType,coinType);
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
	 * 资金划转
	 * @param user
	 * @param params
     * @return
     */
	@Decrypt
	@Authorization
	@ResponseBody
	@RequestMapping(value="transfer",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String transfer(@CurrentUser User user,@Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer type = json.getInteger("type");
			Integer coinType = json.getInteger("coinType");
			String amount = json.getString("amount");
			String password = json.getString("password");

			/*参数校验*/
			if(type==null||coinType==null||StrUtils.isBlank(amount)||StrUtils.isBlank(password)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			/*正则校验*/
			if(!ValidateUtils.isTradePwd(password)){
				return Result.toResult(ResultCode.PARAM_IS_INVALID);
			}

			BigDecimal amountDec = BigDecimalUtils.roundDown(new BigDecimal(amount), coinScaleService.queryByCoin(coinType, -1).getCalculscale());
			return walletBiz.transfer(user, type,coinType,amountDec,password);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}  catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}
	/**
	 * 获取提现信息
	 * @param user
	 * @param params
	 * @return
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="withDraw/info",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String withDrawInfo(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			Integer accountType = json.getInteger("accountType");
			/*参数校验*/
			if(coinType==null || accountType == null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//获取信息
			return walletBiz.withDrawInfo(user, coinType, accountType);
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
	@ResponseBody
	@RequestMapping(value="addWallet",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String addWallet(String key){
		String keys = "yibi";
		if(keys.equals(key)){
			walletBiz.addWallet();
			return "success";
		}else {
			return "解密失败";
		}
	}
}
