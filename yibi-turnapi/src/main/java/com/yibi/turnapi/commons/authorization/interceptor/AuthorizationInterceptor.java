package com.yibi.turnapi.commons.authorization.interceptor;

import com.alibaba.fastjson.JSON;
import com.yibi.core.entity.User;
import com.yibi.turnapi.commons.authorization.annotation.Authorization;
import com.yibi.turnapi.commons.authorization.annotation.Decrypt;
import com.yibi.turnapi.commons.authorization.annotation.Sign;
import com.yibi.turnapi.commons.authorization.manager.TokenManager;
import com.yibi.turnapi.commons.enums.ResultCode;
import com.yibi.turnapi.commons.utils.*;
import com.yibi.turnapi.commons.variables.GlobalParams;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager tokenManager;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        try {
            log.info("地址---->"+request.getRequestURI());
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            /*获取参数*/
            String param = request.getParameter(GlobalParams.PARAM);
            String sign = request.getParameter(GlobalParams.SIGN);
            String token = request.getHeader(GlobalParams.TOKEN);
            String secretkey = request.getParameter(GlobalParams.SRCRETKEY);

            log.info("参数---->params:"+param+",sign="+sign+",token="+token+",key="+secretkey);

            /*参数校验*/
            if((needToCheckSign(method)&&StrUtils.isBlank(sign))
                    ||(!needToCheckAuthorization(method)&&needToDecrypt(method)&&StrUtils.isBlank(secretkey))
                    ){
                log.info("sign为空或key为空");
                returnErrorMessage(response,ResultCode.PARAM_IS_BLANK);
                return false;
            }

            if(!needToCheckAuthorization(method)){
                Map<String, Object> paramMap = null;
                try {
                    paramMap = buildPram(param, needToDecrypt(method), secretkey,false);
                    log.info("params---->"+ValidateUtils.replacePwdOfLog(paramMap.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    returnErrorMessage(response,ResultCode.INTERFACE_DECRYPT_ERROR);
                    return false;
                }

                /*请求超时验证*/
				/*if(paramMap.get("timeStamp")!=null&&checkTimeOut(paramMap)){
					returnErrorMessage(response,ResultCode.INTERFACE_REQUEST_TIMEOUT);
					return false;
				}*/

//                /*记录操作日志*/
//                if(needToRecordRequest(method)){
//                    try {
//                        recordRequest(paramMap,null,request);
//                    }catch (NumberFormatException e) {
//                        e.printStackTrace();
//                        returnErrorMessage(response,ResultCode.PARAM_TYPE_BIND_ERROR);
//                        return false;
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        returnErrorMessage(response,ResultCode.PARAM_TYPE_BIND_ERROR);
//                        return false;
//                    }
//                }
                request.setAttribute(GlobalParams.PARAM, paramMap);
            }else{
                User user = tokenManager.checkToken(token);
                if(user!=null){
                    Map<String, Object> paramMap = null;
                    /*用户状态判断*/
                    if(user.getState()==GlobalParams.FORBIDDEN){
                        returnErrorMessage(response,ResultCode.USER_ACCOUNT_FORBIDDEN);
                        return false;
                    }
                    if(user.getState()==GlobalParams.LOGOFF){
                        returnErrorMessage(response,ResultCode.USER_ACCOUNT_LOGOFF);
                        return false;
                    }


                    /*参数解密*/
                    try {
                        paramMap = buildPram(param, needToDecrypt(method), user.getSecretkey(),true);
                        log.info("params---->"+ValidateUtils.replacePwdOfLog(paramMap.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnErrorMessage(response,ResultCode.INTERFACE_DECRYPT_ERROR);
                        return false;
                    }

					/*超时验证
					if(paramMap.get("timeStamp")!=null&&checkTimeOut(paramMap)){
						returnErrorMessage(response,ResultCode.INTERFACE_REQUEST_TIMEOUT);
						return false;
					}*/
                    /*签名验证*/
                    if(needToCheckSign(method)&&!checkSign(paramMap,user.getSecretkey(),sign)){
                        returnErrorMessage(response,ResultCode.INTERFACE_SIGN_ERROR);
                        return false;
                    }
//                    /*记录请求*/
//                    if(needToRecordRequest(method)){
//                        try {
//                            recordRequest(paramMap,user.getId(),request);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            returnErrorMessage(response,ResultCode.PARAM_IS_INVALID);
//                            return false;
//                        }
//
//                    }

                    request.setAttribute(GlobalParams.CURRENT_USER, user);
                    request.setAttribute(GlobalParams.PARAM, paramMap);
                    return true;
                }else{
                    returnErrorMessage(response,ResultCode.USER_NOT_LOGGED_IN);
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnErrorMessage(response, ResultCode.SYSTEM_INNER_ERROR);
            return false;
        }

        return true;
    }

    /**
     * 是否需要检查登录权限
     * @param method
     * @return boolean
     * @date 2017-12-23
     * @author lina
     */
    private boolean needToCheckAuthorization(Method method){
        return method.getAnnotation(Authorization.class)!=null;
    }

    /**
     * 是否进行签名认证
     * @param method
     * @return boolean
     * @date 2017-12-28
     * @author lina
     */
    private boolean needToCheckSign(Method method){
        return method.getAnnotation(Sign.class)!=null;
    }

    /**
     * 是否进行解密参数
     * @param method
     * @return boolean
     * @date 2017-12-28
     * @author lina
     */
    private boolean needToDecrypt(Method method){
        return method.getAnnotation(Decrypt.class)!=null;
    }

//    /**
//     * 是否需要记录请求地址
//     * @param method
//     * @return boolean
//     * @date 2017-12-23
//     * @author lina
//     */
//    private boolean needToRecordRequest(Method method){
//        TSysparams param = tSysparamsService.getValByKey(SystemParams.RECORDREQUEST_ONOFF);
//        return "1".equals(param.getKeyval())&&method.getAnnotation(RecordRequest.class)!=null;
//    }


    /**
     * 构建param参数
     * @param param
     * @param isEncrypt
     * @param secretKey
     * @throws Exception
     * @return JSONObject
     * @date 2017-12-25
     * @author lina
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> buildPram(String param,boolean isEncrypt,String secretKey,boolean existUser) throws Exception{
        if(StrUtils.isBlank(param)){
            return new HashMap<String, Object>();
        }
        param = param.replaceAll(" ", "+");
        String key = "";
        Map<String, Object> map = null;
        if(isEncrypt){
            if(existUser){
                key = secretKey;
            }else{
                //RSAPrivateKey privateKey = RSA.getPrivateKey(BASE64.decoderByte(GlobalParams.RSA_PRIVATE_KEY));
                //key = RSA.decode(BASE64.decoderByte(secretKey.replaceAll(" ", "+")), privateKey);
                String base64Key = BASE64.encoder(secretKey);
                key = base64Key.substring(0, 16);
            }
            String deParamStr = AES.decrypt(param, key);
            map = (Map<String, Object>)JSON.parse(deParamStr.trim());
            map.put("secretKey", key);
        }else{
            map = (Map<String, Object>)JSON.parse(BASE64.decoder(param));
        }
        return map;
    }


    /**
     * 超时校验
     * @param param
     * @throws Exception
     * @return boolean
     * @date 2017-12-25
     * @author lina
     */
    private boolean checkTimeOut(Map<String, Object> param) throws Exception {
        Long timeStampCli = Long.parseLong((String)param.get("timeStamp"));
        Long timeStampSer = System.currentTimeMillis();
        return (timeStampSer-timeStampCli)/1000>GlobalParams.TIMEOUTINTERVAL;
    }

    /**
     * 签名校验
     * @param param
     * @param secretKey
     */
    private boolean checkSign(Map<String, Object> param,String secretKey,String sign) throws Exception{
        String matchSign = SignUtils.createSign(param, secretKey);
        return sign.equals(matchSign);
    }

//    /**
//     * 记录请求地址
//     * @param param
//     * @param request
//     * @return void
//     * @date 2017-12-25
//     * @author lina
//     * @throws JSONException
//     */
//    public void recordRequest(Map<String, Object> param,Integer userId,HttpServletRequest request) throws JSONException{
//        TRequestRecord rec = new TRequestRecord();
//        rec.setUserid(userId);
//        rec.setDevicenum(param.get("deviceNum")==null?"":param.get("deviceNum").toString());
//        rec.setPhonetype(param.get("systemType") == null ? (byte) 0 : (byte) Integer.parseInt(param.get("systemType").toString()));
//        rec.setIp(IPUtils.ip2long(IPUtils.getIpAddr(request)));
//        rec.setTimestamp(param.get("timeStamp")==null?null:new Timestamp(Long.parseLong(param.get("timeStamp").toString())));
//        rec.setUrl(request.getRequestURI());
//        tRequestRecordService.insertSelective(rec);
//    }

    /**
     * 返回错误信息
     * @param response
     * @param resultCode
     * @throws Exception
     * @return void
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
