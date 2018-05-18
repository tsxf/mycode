package com.mycode.framework.aop;

import sun.reflect.generics.scope.MethodScope;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 蛮小江
 * 2018/5/10 9:42
 */
public class AopProxy implements InvocationHandler {
    private Object target;
    private AopConfig aopConfig;


    public Object getProxy(Object target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public void setAopConfig(AopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method是接口中的method，这里要用target里面的method
        Method m = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        //AOP增强，方法执行前增强
        if (aopConfig.contains(m)) {
            //反射切面增强方法 before
            AopConfig.Aspect aspect = aopConfig.get(m);
            aspect.getMethods()[0].invoke(aspect.getAspect());
        }

        //根据方法名字判断开启的数据库事务类型
        //查询 readOnly,其他 自动提交关闭
        //根据正则去匹配  定义规则

        //开启事务

        Object result = null;
        try {
            result = method.invoke(target, args);
        } catch (Exception e) {
            e.printStackTrace();
            Class<? extends Exception> exceptionClass = e.getClass();
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            //自定义异常类型
            for (Class<? > clazz:exceptionTypes){
                if (exceptionClass == clazz ) {
                    //回滚
                }
            }


        }

        System.out.println(result);

        //AOP增强，方法执行后增强
        if (aopConfig.contains(m)) {
            //反射切面增强方法 after
            AopConfig.Aspect aspect = aopConfig.get(m);
            aspect.getMethods()[1].invoke(aspect.getAspect());
        }

        //事务关闭

        return result;
    }
}
