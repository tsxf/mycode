package com.mycode.rmi.rpc.client;

import com.mycode.rmi.rpc.server.IHello;

/**
 * 蛮小江
 * 2018/6/19 14:05
 */
public class ClientStart {
    public static void main(String[] args) {

        RPCClientProxy proxy = new RPCClientProxy();
        IHello IHello = proxy.createProxy(IHello.class, "localhost", 8000);
        String result = IHello.sayHello("Neo,,,");
        System.out.println(result);

    }

}
