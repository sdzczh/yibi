package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.OrderMakerBiz;
import com.yibi.orderapi.biz.OrderTakerBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/16 0016.
 */

@Controller
@RequestMapping("c2c")
public class OrderC2cController {

    @Autowired
    private OrderMakerBiz orderMakerBiz;

    @Autowired
    private OrderTakerBiz orderTakerBiz;


    /**
     * 商户-下单（买入，卖出）
     * @param user
     * @param params
     * @return
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="maker/order",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String wechatRecharge2(@CurrentUser User user , @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            Integer orderType = json.getInteger("orderType");
            Integer payType = json.getInteger("payType");
            String price = json.getString("price");
            String amount = json.getString("amount");
            String totalMin = json.getString("totalMin");
            String totalMax = json.getString("totalMax");
            String password = json.getString("password");
			
			/*参数校验*/
            if(orderType==null||coinType==null||payType==null||StrUtils.isBlank(amount)||StrUtils.isBlank(price)
                    ||StrUtils.isBlank(totalMin)||StrUtils.isBlank(totalMax)||StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }

            // 商家发布交易
            return  orderMakerBiz.makerReleaseDeal(user, coinType, new BigDecimal(price),
                    new BigDecimal(amount), orderType, new BigDecimal(totalMin), new BigDecimal(totalMax), payType, password);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (NumberFormatException e) {
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
     * 商户的委托列表
     * @param user
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="maker/list",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryMakerListByUser(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            Integer state = json.getInteger("state");
            Integer coinType = json.getInteger("coinType");
            Integer orderType = json.getInteger("orderType");
            if(state==null||coinType==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            return orderMakerBiz.queryOrderList(user,orderType, coinType, state, page, rows);
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
     * 查询实时价格和可用余额
     * @param user
     * @param params
     * @return String
     * @date 2018-2-27
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="maker/walletinfo",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryavailBalanceAndPrice(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            if(coinType==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //查询实时价格和可用余额
            return orderMakerBiz.queryavailBalanceAndPrice(user, coinType);
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
     * 商家-取消订单
     * @param user
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="maker/cancel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String cancelOrder(@CurrentUser User user ,@Params Object params){
       // try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            String password = json.getString("password");
			
			/*参数校验*/

            if(orderId==null || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }

            // 撤销交易
            return orderMakerBiz.cancelOrder(user, orderId,  password);
        /*}catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }*/
    }

    /**
     *
     * @param user
     * @param params
     * @return开始接单，取消接单
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="maker/receipt/one",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String receiptOneOrder(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            if(orderId==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //是否接单
            return orderMakerBiz.receiptOrder(user, orderId);
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
     * 普通界面的商家列表
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */

    @ResponseBody
    @RequestMapping(value="makerlist",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String querymakerList(@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            Integer orderType = json.getInteger("orderType");
            Integer coinType = json.getInteger("coinType");
            if(orderType==null||coinType==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //充值
            return orderMakerBiz.queryOrderList( coinType, orderType, page, rows);
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
     * 普通买入
     * @param user
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="taker/buy",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String takerBuy(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            String amount = json.getString("amount");
            Integer orderId = json.getInteger("orderId");

			/*参数校验*/
            if(coinType==null||StrUtils.isBlank(amount)
                    ){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //普通用户买入
            return orderTakerBiz.takerReleaseDeal(user, coinType,
                    new BigDecimal(amount), orderId, "", GlobalParams.ORDER_TYPE_BUY);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (NumberFormatException e) {
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
     * 普通卖出
     * @param user
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="taker/sale",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String takersale(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            Integer orderId = json.getInteger("orderId");
            String password = json.getString("password");
            String amount = json.getString("amount");
			
			/*参数校验*/
            if(coinType==null||StrUtils.isBlank(amount)
                    ||StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            //普通用户买入
            return orderTakerBiz.takerReleaseDeal(user, coinType,
                    new BigDecimal(amount), orderId, password,GlobalParams.ORDER_TYPE_SALE);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (NumberFormatException e) {
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
     * 订单列表
     * @param user
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="taker/orderlist",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryTakerListByUser(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            Integer state = json.getInteger("state");
            Integer coinType = json.getInteger("coinType");
            Integer orderType = json.getInteger("orderType");
            Integer userType = json.getInteger("userRole");
            if(state==null||coinType==null||userType==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //订单列表
            return orderTakerBiz.queryOrderList(user, coinType,orderType, state,userType, page, rows);
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
     * 订单详情
     * @param user
     * @param params
     * @return String
     * @date 2018-2-26
     * @author lina
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="taker/orderinfo",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryTakerInfo(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            if(orderId==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //订单列表
            return orderTakerBiz.queryOrderInfo(user, orderId);
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
    @RequestMapping(value="taker/cancel",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String cancelTakerOrder(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            if(orderId==null){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }

            //取消订单
            return orderTakerBiz.cancelTakerOrder(user, orderId);
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


    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="taker/confirm-pay",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String confirmPayment(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            Integer payId = json.getInteger("payType");
            String password = json.getString("password");
			
			/*参数校验*/
            if(orderId ==null||payId ==null
                    ||StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }

            //确认付款
            return orderTakerBiz.confirmPayment(user, orderId,payId, password);
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

    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="taker/confirm-receipt",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String confirmReceipt(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            String password = json.getString("password");
			
			/*参数校验*/
            if(orderId ==null
                    || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
			
			/*正则校验*/
            if(!ValidateUtils.isTradePwd(password)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }

            //确认付款
            return orderTakerBiz.confirmReceipt(user, orderId, password);
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
    @RequestMapping(value="taker/appeal",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public String appeal(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderId = json.getInteger("orderId");
            String remark = json.getString("reason");


            //客服申诉
            return orderTakerBiz.orderAppeal(user, orderId,remark);
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
    @RequestMapping(value="userinfo",method=RequestMethod.GET,produces="application/json;charset=utf-8")
    public String queryUserInfo(@CurrentUser User user ,@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String userPhone = json.getString("userPhone");

            if(StringUtils.isBlank(userPhone)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return orderTakerBiz.queryUserInfo(user, userPhone);
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
