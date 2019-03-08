package com.mycode.rmi.rpc.client;

import java.lang.reflect.Proxy;

/**
 * 蛮小江
 * 2018/6/19 14:06
 */
public class RPCClientProxy {
    public <T> T createProxy(final Class<T> interfaces, final String host, final int port) {
        return (T) Proxy.newProxyInstance(interfaces.getClassLoader(), new Class[]{interfaces}, new RemoteInvocationHandler(host, port));
    }
}
