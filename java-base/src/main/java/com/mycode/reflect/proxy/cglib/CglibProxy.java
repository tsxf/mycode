package com.mycode.reflect.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 13:58
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class CglibProxy implements MethodInterceptor {

    public  Object getProxy(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        //设置要代理的父类
        enhancer.setSuperclass(clazz);
        //设置回调
        enhancer.setCallback(this);
        return  enhancer.create();

    }

    //业务的增强
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        //方法执行前要执行的动作
        System.out.println("==========A========");
        Object o1 = (String )methodProxy.invokeSuper(o, objects);
        System.out.println("返回值："+o1);
        //方法执行后要执行的动作
        System.out.println("==========B========");
        return o1;
    }

}
