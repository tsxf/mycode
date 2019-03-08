package com.mycode.rmi.rpc.server;

/**
 * 蛮小江
 * 2018/6/19 8:41
 */
public class HelloServiceImpl implements IHello {
    @Override
    public String sayHello(String name) {
        return "IHello "+name;
    }
}
