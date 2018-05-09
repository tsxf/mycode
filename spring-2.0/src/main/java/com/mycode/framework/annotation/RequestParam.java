package com.mycode.framework.annotation;

import java.lang.annotation.*;

/**
 * 请求参数映射
 * Created by 江富 on 2018/4/25
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default  "";
}
