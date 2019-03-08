package com.mycode.zookeeper.registerycenter.server.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 蛮小江
 * 2018/6/19 20:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcAnnotation {
    //对外发布的服务地址
   Class<?> value();
   //服务的版本
   String version() default "";
}
