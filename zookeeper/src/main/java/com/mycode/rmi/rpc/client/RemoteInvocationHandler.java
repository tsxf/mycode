package com.mycode.rmi.rpc.client;

import com.mycode.rmi.rpc.server.RpcRequest;

import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 蛮小江
 * 2018/6/19 14:06
 */
public class RemoteInvocationHandler implements InvocationHandler {
    private  String host;
    private  int port;

    public RemoteInvocationHandler(String host,int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //组装请求
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamters(args);
        //通过TCP协议进行传输
        TCPTransport transport = new TCPTransport(host, port);
        //发送请求
        return  transport.send(request);
    }
}
