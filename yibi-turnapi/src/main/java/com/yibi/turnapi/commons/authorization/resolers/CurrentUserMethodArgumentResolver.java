package com.yibi.turnapi.commons.authorization.resolers;

import com.yibi.core.entity.User;
import com.yibi.turnapi.commons.authorization.annotation.CurrentUser;
import com.yibi.turnapi.commons.variables.GlobalParams;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
* @author lina 
* @date 2017-12-23
* @version V1.0
 */
@Service
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter arg0,
                                  ModelAndViewContainer arg1, NativeWebRequest arg2,
                                  WebDataBinderFactory arg3) throws Exception {
		//返回用户
		User currentUser = (User) arg2.getAttribute(GlobalParams.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
		if(currentUser!=null){
			return currentUser;
		}
		throw new MissingServletRequestPartException(GlobalParams.CURRENT_USER);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//如果参数类型是User并且有CurrentUser注解则支持
		if(parameter.getParameterType().isAssignableFrom(User.class)&&parameter.hasParameterAnnotation(CurrentUser.class)){
			return true;
		}
		return false;
	}

}
