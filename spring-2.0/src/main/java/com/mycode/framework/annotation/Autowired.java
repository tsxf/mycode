package com.mycode.framework.annotation;

import java.lang.annotation.*;

/**
 * 自动注入
 * Created by 江富 on 2018/4/25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
