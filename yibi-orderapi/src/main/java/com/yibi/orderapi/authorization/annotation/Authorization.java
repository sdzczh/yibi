package com.yibi.orderapi.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在controller方法中使用此注解，该方法映射时会检查用户时候登录，如果未登录则返回401错误
* @author lina 
* @date 2017-12-23
* @version V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

}
