package com.mycode.reflect.proxy.jdk;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/7 8:41
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Target implements  TargetService {
    private  String name;

    public Target(String name) {
        this.name = name;
    }

    @Override
    public  String doBiz(String message){
        message =  message +" " + name;
        System.out.println(message);
        return message;
    }
}
