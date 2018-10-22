package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.DigBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/7/19 0019.
 */

@Controller
@RequestMapping("dig")
public class DigController {

    @Autowired
    private DigBiz digBiz;

    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="digPageInfo",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String digPageInfo(@CurrentUser User user, @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            //客户端存储的用户等级
            Integer level = json.getInteger("level");
            return digBiz.getDigPageInfo(user, level);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 开启挖矿
     * @param user
     * @param params
     * @return
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="dig",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String dig(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return digBiz.updateDigState(user);
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

    /**
     * 收取挖矿
     * @param user
     * @param params
     * @return String
     * @date 2018-4-10
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="collect",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String collect(@CurrentUser User user ,@Params Object params){
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
            return digBiz.collectDig(user,coinType);
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

    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String DigListQuery(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            return digBiz.queryDigList(user, page, rows);
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


    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="soul/detail",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryUserSoulInfo(@CurrentUser User user, @Params Object params){
        try {

            return digBiz.queryUserSoulInfo(user);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="soul/rank",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String querySoulRank(@CurrentUser User user, @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            return digBiz.querySoulRank(user,page,rows);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="soul/pageInfo",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String querySoulPageInfo(@CurrentUser User user, @Params Object params){
        try {

            return digBiz.querySoulPageInfo(user);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }


    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="soul/sign",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String addCalculforce(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            JSONObject json = (JSONObject)params;
            Integer type = json.getInteger("type");

            if(type == null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return digBiz.addSignSoul(user,type);
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

    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="soul/share/result",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String shareReward(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer type = json.getInteger("type");
			/*参数校验*/
            if(type==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return digBiz.addShareSoul(user, type);
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
    @RequestMapping(value="soul/flow",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String querySoulFlow(@CurrentUser User user, @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            return digBiz.querySoulFlow(user,page,rows);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.RESULE_DATA_NONE);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="withdraw",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String digAccountWithdraw(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            Integer type = json.getInteger("type");
            String password = json.getString("password");
            String accountNum = json.getString("accountNum");

			/*参数校验*/

            if(type==null||coinType==null||
                    StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }

            //提现
            return digBiz.addDigWithdraw(user,password,accountNum,type,coinType);
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
