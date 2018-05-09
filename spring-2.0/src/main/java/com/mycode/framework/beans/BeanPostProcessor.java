package com.mycode.framework.beans;

/**
 * Created by 江富 on 2018/4/25
 */
//用作事件的监听
public class BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
