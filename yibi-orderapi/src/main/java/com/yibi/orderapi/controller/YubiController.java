package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.YubiBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/23 0023.
 */
@Controller
@RequestMapping("/yubi")
public class YubiController {
    @Autowired
    private YubiBiz yubiBiz;

    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="transfer",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String transfer(@CurrentUser User user , @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            Integer type = json.getInteger("type");
            String amount = json.getString("amount");
            String password = json.getString("password");

			/*参数校验*/

            if(type==null||coinType==null|| StrUtils.isBlank(amount)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            //资金划转
            return yubiBiz.transfer(user, password, new BigDecimal(amount),type,coinType);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }
        catch (NumberFormatException e) {
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
    @RequestMapping(value="flows",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryFlows(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");

			/*参数校验*/

            if(coinType==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            //提现
            return yubiBiz.queryFlows(user,coinType,page,rows);
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

    @RequestMapping(value="balance",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryBalance(@CurrentUser User user ,@Params Object params){
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
            //查询余额
            return yubiBiz.queryBalance(user, coinType, accountType);
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
