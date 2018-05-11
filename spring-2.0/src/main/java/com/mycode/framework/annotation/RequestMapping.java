package com.mycode.framework.annotation;

import java.lang.annotation.*;

/**
 * 请求url
 * Created by 蛮小江 on 2018/4/25
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default  "";
}
