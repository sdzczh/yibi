package com.yibi.openapi.commons.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在 Controller 的方法参数中使用此注解，对params参数解密
* @Description: TODO
* @author lina 
* @date 2017-12-28
* @version V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {

}
