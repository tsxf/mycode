package com.mycode.reflect.proxy.custom;

import java.lang.reflect.Method;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:44
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public interface MyInvocationHandler {
    
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
