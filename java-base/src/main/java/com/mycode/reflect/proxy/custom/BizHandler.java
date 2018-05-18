package com.mycode.reflect.proxy.custom;

import java.lang.reflect.Method;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:53
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class BizHandler implements  MyInvocationHandler{
    private  Object target;
    public BizHandler(Object target) {
        this.target = target;
    }

    public  Object getProxy() throws Exception {
        return   MyProxy.newProxyInstance(new MyClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("============A==========");
        Object result = method.invoke(target, args);
        System.out.println("============B==========");
        return result;
    }
}
