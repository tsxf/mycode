package com.mycode.reflect.proxy.custom;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/9 14:28
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Target target = new Target();;
        BizHandler bizHandler = new BizHandler(target);
        TargetService proxy = (TargetService)bizHandler.getProxy();
        String result = proxy.sayHello("chenchao");
        System.out.println("result:"+result);

    }
}
