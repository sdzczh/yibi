package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.OrderBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 现货、杠杆交易
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderBiz orderBiz;

    /**
     * 获取用户进入交易界面的场景信息
     *
     * @return
     */
    @Sign
    @Authorization
    @RequestMapping(value = "/mainpageInfo", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String mainpageInfo(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            /*参数校验*/
            if (orderCoin == null || unitCoin == null || levFlag == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return orderBiz.mainpageInfo(user, orderCoin, unitCoin, levFlag);
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
     * 限价买入
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/limitPriceBuy", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String limitPriceBuy(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String price = json.getString("price");
            String amount = json.getString("amount");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(price) || StrUtils.isBlank(amount) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderBiz.limitPriceBuy(user, orderCoin, unitCoin, levFlag, price, amount, password);
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
     * 限价卖出
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/limitPriceSale", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String limitPriceSale(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String price = json.getString("price");
            String amount = json.getString("amount");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(price) || StrUtils.isBlank(amount) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderBiz.limitPriceSale(user, orderCoin, unitCoin, levFlag, price, amount, password);
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
     * 市价买入
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/marketPriceBuy", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String marketPriceBuy(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String total = json.getString("total");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(total) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderBiz.marketPriceBuy(user, orderCoin, unitCoin, levFlag, total, password);
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
     * 市价卖出
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/marketPriceSale", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String marketPriceSale(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String amount = json.getString("amount");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(amount) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderBiz.marketPriceSale(user, orderCoin, unitCoin, levFlag, amount, password);
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
     * 当前委托或历史记录
     * @param user
     * @param params
     * @return
     */
    @Authorization
    @Sign
    @RequestMapping(value = "/orderRecord", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String orderRecord(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer type = json.getInteger("type");
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            if (page == null) {
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            /*参数校验*/
            if (type == null || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return orderBiz.orderRecord(user, orderCoin, unitCoin, type, pageModel);
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
     * 撤销订单
     * @param user
     * @param params
     * @return
     */
    @Sign
    @Authorization
    @RequestMapping(value = "/orderCancel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String orderCancel(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer id = json.getInteger("id");
            /*参数校验*/
            if (id == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return orderBiz.orderCancel(id);
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
     * 订单详情
     * @param params
     * @return
     */
    @Sign
    @Authorization
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String orderDetail(@Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer id = json.getInteger("id");
            /*参数校验*/
            if (id == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            return orderBiz.orderDetail(id);
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
     * 交易挖矿页面信息
     * @param user
     * @param params
     * @return
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="dealDigRecordList",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String dealDigRecordList(@CurrentUser User user, @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            return orderBiz.dealDigRecordList(user, page == null ? 0 : page, rows == null ? 5 : rows);

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
     * 验证实名状态和交易密码
     * @param user
     * @param params
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value="checkRealNameAndOrderPassWord",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String checkRealNameAndOrderPassWord(@CurrentUser User user){
        try {

            return orderBiz.checkRealNameAndOrderPassWord(user);

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
     * 币币小K图
     * @param user
     * @param params
     * @return
     */
    @Sign
    @ResponseBody
    @RequestMapping(value="minKLine",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String minKLine(@Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            return orderBiz.minKLine(orderCoin, unitCoin);

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
