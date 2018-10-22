package com.yibi.openapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.openapi.biz.WalletBiz;
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

import java.math.BigDecimal;

/**
 * 提现、充值相关接口
 */
@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController {

    @Autowired
    private WalletBiz walletBiz;

    /**
     * 提现到一币现货账户
     *
     * @param params
     * @return
     * @date 2018-5-17
     * @author ldz
     */
    @Sign
    @ResponseBody
    //@RequestMapping(value = "withdrawMoney", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object withdrawMoney(@Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer coinType = CoinType.DK;
            Integer type = GlobalParams.PAY_SPOT;//提取到现货账户
            String password = json.getString("password");//交易密码
            String account = json.getString("yibiaccount");
            BigDecimal money = json.getBigDecimal("money");
            /*参数校验*/
            if (StrUtils.isBlank(account) || StrUtils.isBlank(password) || money == null || money.compareTo(BigDecimal.ZERO) == 0) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            if (money.compareTo(BigDecimal.ZERO) < 0) {
                return Result.toResultNotEncrypt(ResultCode.AMOUNT_NEGATIVE);
            }
            /*正则校验*/
            if (!ValidateUtils.isTradePwd(password) || !ValidateUtils.isPhone(account)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_INVALID);
            }
            //提现
            return walletBiz.withdrawMoney(account, password, type, GlobalParams.ACCOUNT_TYPE_GAME, coinType, money);
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


    /**
     * 通过现货账户充值
     *
     * @param params
     * @return
     * @date 2018-5-17
     * @author ldz
     */
    @Sign
    @ResponseBody
    @RequestMapping(value = "rechargeMoney", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object rechargeMoney(@Params Object params) {
        try {
            if (!(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer coinType = CoinType.DK;
            Integer type = GlobalParams.ACCOUNT_TYPE_SPOT;//现货账户
            String password = json.getString("password");//交易密码
            String account = json.getString("yibiaccount");
            BigDecimal money = json.getBigDecimal("money");
            /*参数校验*/
            if (StrUtils.isBlank(account) || StrUtils.isBlank(password) || money == null || money.compareTo(BigDecimal.ZERO) == 0) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            if (money.compareTo(BigDecimal.ZERO) < 0) {
                return Result.toResultNotEncrypt(ResultCode.AMOUNT_NEGATIVE);
            }
            /*正则校验*/
            if (!ValidateUtils.isTradePwd(password) || !ValidateUtils.isPhone(account)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_INVALID);
            }
            //充值游戏
            return walletBiz.rechargeGame(account, password, type, GlobalParams.ACCOUNT_TYPE_GAME, coinType, money);
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

    /**
     * 查询现货钱包中可用的DK币数量
     *
     * @param params
     * @return
     */
    @Sign
    @ResponseBody
    @RequestMapping(value = "queryDK", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Object queryDK(@Params Object params) {
        try {
            if (!(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer coinType = CoinType.DK;
            String account = json.getString("yibiaccount");
            /*参数校验*/
            if (StrUtils.isBlank(account)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            /*正则校验*/
            if (!ValidateUtils.isPhone(account)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_INVALID);
            }
            //查看可用DK数量
            return walletBiz.queryAvailBalance(account, coinType);
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
