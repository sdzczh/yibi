package com.yibi.turnapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.yibi.core.entity.User;
import com.yibi.turnapi.commons.authorization.annotation.Authorization;
import com.yibi.turnapi.commons.authorization.annotation.CurrentUser;
import com.yibi.turnapi.commons.authorization.annotation.Decrypt;
import com.yibi.turnapi.commons.authorization.annotation.Params;
import com.yibi.turnapi.commons.enums.CoinType;
import com.yibi.turnapi.commons.enums.ResultCode;
import com.yibi.turnapi.commons.utils.BigDecimalUtils;
import com.yibi.turnapi.commons.utils.Result;
import com.yibi.turnapi.commons.utils.StrUtils;
import com.yibi.turnapi.controller.base.BaseController;
import com.yibi.turnapi.service.FortuneWheelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;


@Controller
@RequestMapping("/fortuneWheel")
@Slf4j
public class FortuneWheelController extends BaseController {


    @Autowired
    private FortuneWheelService fortuneWheelService;

    /**
     * 大转盘扣除
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "deductCoin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @Authorization
    @Decrypt
    public Object deductCoin(@Params Object params, @CurrentUser User user) {
        try {
            log.info("params:{}",params);
            if (params == null ) {
                log.info("instanceof:{}",params instanceof JSONObject);
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }

            log.info(user.getPhone());
            JSONObject json = (JSONObject)params;
            Integer coinType = json.getInteger("coinType");
            String amount = json.getString("amount");
            log.info(amount);
            /*参数校验*/
            if (StrUtils.isBlank(amount) || coinType == null) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            BigDecimal amountDec = BigDecimalUtils.roundDown(new BigDecimal(amount), CoinType.getScale(coinType));
            log.info("格式化后的数量"+amountDec);
            return fortuneWheelService.deductCoin(user, amountDec, coinType);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 大转盘奖励
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "reward", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @Authorization
    @Decrypt
    public Object reward(@Params Object params, @CurrentUser User user) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer type = json.getInteger("type");//1:增加魂力 2：增加币
            Integer coinType = json.getInteger("coinType");
            String amount = json.getString("amount");
            /*参数校验*/
            if (type == null || coinType == null || StrUtils.isBlank(amount)) {
                return Result.toResultNotEncrypt(ResultCode.PARAM_IS_BLANK);
            }
            BigDecimal amountDec = BigDecimalUtils.roundDown(new BigDecimal(amount), CoinType.getScale(coinType));
            return fortuneWheelService.reward(user, type, coinType, amountDec);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResultNotEncrypt(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

}
