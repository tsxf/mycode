package com.mycode.framework.annotation;

import java.lang.annotation.*;


/**
 * 业务逻辑注入入口
 * Created by 蛮小江 on 2018/4/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}


































