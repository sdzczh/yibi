package com.yibi.orderapi.authorization;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yibi.common.encrypt.AES;
import com.yibi.common.encrypt.BASE64;
import com.yibi.common.encrypt.RSA;
import com.yibi.common.utils.SignUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.Constants;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.Decrypt;
import com.yibi.orderapi.authorization.annotation.Sign;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private UserService userService;
	
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
			String param = request.getParameter(Constants.PARAM);
			String sign = request.getParameter(Constants.SIGN);
			String token = request.getHeader(Constants.TOKEN);
			String secretkey = request.getParameter(Constants.SRCRETKEY);
			
			log.info("参数---->params:{},sign={},token={},key={}",param,sign,token,secretkey);
			
			/*参数校验*/
			if((needToCheckSign(method)&& StringUtils.isBlank(sign))
					||(!needToCheckAuthorization(method)&&needToDecrypt(method)&&StringUtils.isBlank(secretkey))
					){
				log.info("sign为空或key为空");
				returnErrorMessage(response, ResultCode.PARAM_IS_BLANK);
				return false;
			}
				
			if(!needToCheckAuthorization(method)){
				Map<String, Object> paramMap = null;
				try {
					paramMap = buildPram(param, needToDecrypt(method), secretkey,false);
					log.info("params---->"+ ValidateUtils.replacePwdOfLog(paramMap.toString()));
				} catch (Exception e) {
					e.printStackTrace();
					returnErrorMessage(response,ResultCode.INTERFACE_DECRYPT_ERROR);
					return false;
				}
				
				request.setAttribute(Constants.PARAM, paramMap);
			}else{
				User user = queryUserByToken(token);
				if(user!=null){
					Map<String, Object> paramMap = null;
					/*用户状态判断*/
					if(user.getState()== GlobalParams.FORBIDDEN){
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
					request.setAttribute(Constants.CURRENT_USER, user);
					request.setAttribute(Constants.PARAM, paramMap);
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


	public User queryUserByToken(String token ){
		if(StringUtils.isBlank(token)){
			return null;
		}
		Map queryMap = Maps.newHashMap();
		queryMap.put("token",token);
		List<User> users = userService.selectAll(queryMap);
		return users ==null||users.isEmpty()?null:users.get(0);
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
		if(StringUtils.isBlank(param)){
			return new HashMap<String, Object>();
		}
		param = param.replaceAll(" ", "+");
		String key = "";
		Map<String, Object> map = null;
		if(isEncrypt){			
			if(existUser){
				key = secretKey;
			}else{
				RSAPrivateKey privateKey = RSA.getPrivateKey(BASE64.decoderByte(Constants.RSA_PRIVATE_KEY));
				key = RSA.decode(BASE64.decoderByte(secretKey.replaceAll(" ", "+")), privateKey);
			}
			String deParamStr = AES.decrypt(param, key);
			map = (Map<String, Object>)JSON.parse(deParamStr);
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
		return (timeStampSer-timeStampCli)/1000>Constants.TIMEOUTINTERVAL;
	}
	
	/**
	 * 签名校验
	 * @param param
	 * @param secretKey  
	 */
	private boolean checkSign(Map<String, Object> param,String secretKey,String sign) throws Exception{
		String matchSign = SignUtils.createSign(param, secretKey);
		System.out.println(matchSign);
		return sign.equals(matchSign);
	}

	
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
        out.print(Result.toResult(resultCode));
        out.flush();
    }
	
}
