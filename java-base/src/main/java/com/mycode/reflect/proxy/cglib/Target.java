package com.mycode.reflect.proxy.cglib;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:10
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Target implements IService {

    @Override
    public String sayHello(String name) {
        String message = "Hello " + name;
        System.out.println("====原始目标对象：" + message);
        return message;
    }
}

interface IService {
    String sayHello(String name);
}
