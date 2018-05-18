package com.mycode.reflect.proxy.jdk;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/7 8:44
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Client {
    public static void main(String[] args) throws  Exception {
        Target target = new Target("chenchao");
        BizHandler bizHandler = new BizHandler(target);
        //生成代理对象
        TargetService proxy = bizHandler.getProxy();
        //生成字节码写入本地
        bizHandler.generateProxyClass();
        proxy.doBiz("Hello");
        System.out.println("=====end");
    }
}
