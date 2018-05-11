package com.mycode.framework.annotation;

import java.lang.annotation.*;

/**
 * 页面交互
 * Created by 蛮小江 on 2018/4/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
