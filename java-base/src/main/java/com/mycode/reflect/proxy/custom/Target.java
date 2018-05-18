package com.mycode.reflect.proxy.custom;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:53
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Target implements  TargetService {
    @Override
    public String sayHello(String message) {
        message = "Hello " +message;
        System.out.println("==================调用原始方法："+message);
        return message;
    }
}
