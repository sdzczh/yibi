package com.yibi.openapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.service.UserService;
import com.yibi.openapi.biz.UserBiz;
import com.yibi.openapi.commons.authorization.annotation.Params;
import com.yibi.openapi.commons.authorization.annotation.Sign;
import com.yibi.openapi.commons.enums.ResultCode;
import com.yibi.openapi.commons.utils.Result;
import com.yibi.openapi.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户相关接口
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserBiz userBiz;

    /**
     * 验证用户名密码是否可用
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "validateUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @Sign
    public Object validateUser(@Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            String phone = json.getString("account");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(phone) || StrUtils.isBlank(password)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            /*正则校验*/
            if (!ValidateUtils.isPhone(phone)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_INVALID);
            }
            return userBiz.validateUser(phone, password);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 用户注册
     *
     * @return String
     * @date 2017-12-26
     * @author lina
     */
    @Sign
    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object register(@Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            //手机号
            String phone = json.getString("account");
            //密码
            String password = json.getString("password");
            //推荐人手机号(推荐码)
            String referPhone = json.getString("invitecode");

            /*参数校验*/
            if (StrUtils.isBlank(phone) || StrUtils.isBlank(password)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            /*正则校验*/
            if (!ValidateUtils.isPhone(phone) || !ValidateUtils.isPhone(referPhone) || !ValidateUtils.isDigitalAndWord(password)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_INVALID);
            }
            //注册
            return userBiz.register(phone, password, referPhone);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.SYSTEM_INNER_ERROR);
        }

    }

    /**
     * 密码修改
     *
     * @return String
     */
    @Sign
    @ResponseBody
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object updatePwd(@Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            String account = json.getString("account");
            String password = json.getString("password");
            String oldPassword = json.getString("oldPassword");

            /*参数校验*/
            if (StrUtils.isBlank(password) || StrUtils.isBlank(oldPassword) || StrUtils.isBlank(account)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            /*正则校验*/
            if (!ValidateUtils.isDigitalAndWord(oldPassword) || !ValidateUtils.isDigitalAndWord(password) || !ValidateUtils.isPhone(account)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_INVALID);
            }
            //修改密码
            return userBiz.updatePwd(account, oldPassword, password);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.SYSTEM_INNER_ERROR);
        }

    }

}
