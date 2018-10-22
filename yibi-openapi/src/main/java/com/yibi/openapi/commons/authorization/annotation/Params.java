package com.yibi.openapi.commons.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在 Controller 的方法参数中使用此注解，该方法在映射时会注入当前请求参数 JSONObject类型
* @author lina 
* @date 2017-12-23
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Params {

}
