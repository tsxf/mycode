package com.mycode.rmi.rpc.server;

/**
 * 蛮小江
 * 2018/6/19 13:47
 */
public class ServerStart {
    public static void main(String[] args) {
        IHello service = new HelloServiceImpl();
        RpcServer server = new RpcServer();
        server.publish(service, 8000);

    }
}
