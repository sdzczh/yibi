package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.ChatTransferBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/7/13 0013.
 */

@RequestMapping("/talk/transfer")
@Controller
public class ChatTransferController {

    @Autowired
    private ChatTransferBiz chatTransferBiz;

    /**
     * 发送转账
     * @param user
     * @param params
     * @return String
     * @date 2018-5-19
     * @author lina
     */
    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="send",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String leaveGroup(@CurrentUser User user , @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String phone = json.getString("phone");
            Integer accountType = json.getInteger("accountType");
            Integer coinType = json.getInteger("coinType");
            String amount = json.getString("amount");
            String note = json.getString("note");
            String password = json.getString("password");
			/*参数校验*/
            if(accountType ==null||coinType ==null|| StrUtils.isBlank(password)||StrUtils.isBlank(amount)||StrUtils.isBlank(phone) ){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatTransferBiz.sendFransfer(user, phone, accountType, coinType, BigDecimalUtils.roundDown(amount, 4), note, password);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
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
     * 获取转账详情
     * @param user
     * @param params
     * @return
     * @return String
     * @date 2018-5-22
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="detail",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryTransferDetail(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer id = json.getInteger("id");

			/*参数校验*/
            if(id ==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return chatTransferBiz.queryTransferDetail(user, id);
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
     * 获取转账记录
     * @param user
     * @param params
     * @return String
     * @date 2018-5-22
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="list",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryTransferList(@CurrentUser User user , @Params Object params){
        try {

            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer type = json.getInteger("type");
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");

			/*参数校验*/
            if(type ==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return chatTransferBiz.queryTransferList(user,type,page,rows);
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
