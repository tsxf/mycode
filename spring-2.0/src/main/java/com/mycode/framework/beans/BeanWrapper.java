package com.mycode.framework.beans;

import com.mycode.framework.aop.AopConfig;
import com.mycode.framework.aop.AopProxy;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

/**
 * Created by 蛮小江 on 2018/4/25
 */
public class BeanWrapper {
    //还会用到观察者模式
    //1. 支持事件响应，会有一个监听
    private BeanPostProcessor postProcessor;
    //包装beanDefinition
    private Object wrapperInstance;
    //原始的通过反射new出来，要把包装起来，存下来
    private Object originalInstance;

    //初始化代理类
    private  AopProxy aopProxy = new AopProxy();

    public BeanWrapper(Object instance) {
        this.originalInstance = instance;
        this.wrapperInstance = aopProxy.getProxy(instance);

        try {
            generateProxyClass();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void  generateProxyClass() throws  Exception{
        byte[] classFile = ProxyGenerator.generateProxyClass("com.sun.proxy.$Proxy23", originalInstance.getClass().getInterfaces());
        FileOutputStream out = new FileOutputStream("com.sun.proxy.$Proxy23.class");
        out.write(classFile);
        out.flush();


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

    /**
     * 设置Aop切面配置信息
     */
    public void setAopConfig(AopConfig aopConfig){
        aopProxy.setAopConfig(aopConfig);
    }


}
