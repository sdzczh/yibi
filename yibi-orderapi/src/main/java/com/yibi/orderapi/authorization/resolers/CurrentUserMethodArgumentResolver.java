package com.yibi.orderapi.authorization.resolers;

import com.yibi.core.entity.User;
import com.yibi.orderapi.Constants;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
* @author lina 
* @date 2017-12-23
* @version V1.0
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public Object resolveArgument(MethodParameter arg0,
			ModelAndViewContainer arg1, NativeWebRequest arg2,
			WebDataBinderFactory arg3) throws Exception {
		//返回用户
		User currentUser = (User) arg2.getAttribute(Constants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
		return currentUser;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//如果参数类型是User并且有CurrentUser注解则支持
		return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
	}

}
