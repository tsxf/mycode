package com.mycode.framework.beans;

/**
 * Created by 江富 on 2018/4/25
 */
public class BeanWrapper {
    //还会用到观察者模式
    //1. 支持事件响应，会有一个监听
    private BeanPostProcessor postProcessor;
    //包装beanDefinition
    private Object wrapperInstance;
    //原始的通过反射new出来，要把包装起来，存下来
    private Object originalInstance;


    public BeanWrapper(Object instance) {
        this.originalInstance = instance;
        this.wrapperInstance = instance;
    }

    public BeanPostProcessor getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(BeanPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    public void setWrapperInstance(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }

    //返回代理以后的Class
    //可能会是这个$Proxy0
    public  Class<?> getWrapperClass(){
        return  this.wrapperInstance.getClass();
    }



    public Object getOriginalInstance() {
        return originalInstance;
    }

    public void setOriginalInstance(Object originalInstance) {
        this.originalInstance = originalInstance;
    }


}
