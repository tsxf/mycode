package com.gupaoedu.vip.spring.annotation;

import java.lang.annotation.*;

/**
 * Created by 江富 on 2018/4/22
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default  "";
}
