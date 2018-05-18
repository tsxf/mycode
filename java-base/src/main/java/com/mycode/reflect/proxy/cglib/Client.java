package com.mycode.reflect.proxy.cglib;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:15
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Client {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Target target =(Target) proxy.getProxy(Target.class);
        target.sayHello("chenchao");

    }
}
