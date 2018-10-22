package com.yibi.openapi.commons.authorization.interceptor;
import com.alibaba.fastjson.JSON;
import com.yibi.common.encrypt.BASE64;
import com.yibi.common.encrypt.MD5;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.openapi.commons.authorization.annotation.Sign;
import com.yibi.openapi.commons.enums.ResultCode;
import com.yibi.openapi.commons.utils.Result;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

@Component
@Log4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        try {
            log.info("地址---->" + request.getRequestURI());
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            /*获取参数*/
            String param = request.getParameter(GlobalParams.PARAM);
            String sign = request.getParameter(GlobalParams.SIGN);

            log.info("参数---->params:" + param + ",sign=" + sign);

            /*参数校验*/
            if ((needToCheckSign(method) && StrUtils.isBlank(sign)) || StrUtils.isBlank(param)) {
                log.info("sign或param为空");
                returnErrorMessage(response, ResultCode.PARAM_IS_BLANK);
                return false;
            }
            Map<String, Object> paramMap = null;
            try {
                //验证sign，解密param
                paramMap = decryptParams(param, sign);
                if (paramMap == null) {
                    returnErrorMessage(response, ResultCode.INTERFACE_DECRYPT_ERROR);
                    return false;
                }
                log.info("params---->" + paramMap.toString());
            } catch (Exception e) {
                e.printStackTrace();
                returnErrorMessage(response, ResultCode.INTERFACE_DECRYPT_ERROR);
                return false;
            }
            request.setAttribute(GlobalParams.PARAM, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            returnErrorMessage(response, ResultCode.SYSTEM_INNER_ERROR);
            return false;
        }
        return true;
    }

    private Map<String, Object> decryptParams(String params, String sign) {
        String decryptParams = "";
        try {
            if (!StrUtils.isBlank(params)) {
                decryptParams = BASE64.decoder(params);
            }
            if (sign.equals(MD5.getMd5(decryptParams + GlobalParams.SECRET))) {
                return (Map<String, Object>) JSON.parse(decryptParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 是否进行签名认证
     *
     * @param method
     * @return boolean
     * @date 2017-12-28
     * @author lina
     */
    private boolean needToCheckSign(Method method) {
        return method.getAnnotation(Sign.class) != null;
    }


    /**
     * 返回错误信息
     *
     * @param response
     * @param resultCode
     * @return void
     * @throws Exception
     * @date 2017-12-25
     * @author lina
     */
    private void returnErrorMessage(HttpServletResponse response, ResultCode resultCode) throws Exception {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(Result.toResultNotEncrypt(resultCode));
        out.flush();
    }



}
