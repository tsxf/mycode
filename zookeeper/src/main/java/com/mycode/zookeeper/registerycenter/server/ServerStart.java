package com.mycode.zookeeper.registerycenter.server;

import com.mycode.zookeeper.registerycenter.server.rpc.HelloServiceImpl;
import com.mycode.zookeeper.registerycenter.server.rpc.HelloServiceImpl2;
import com.mycode.zookeeper.registerycenter.server.rpc.IHello;
import com.mycode.zookeeper.registerycenter.server.rpc.RpcServer;
import com.mycode.zookeeper.registerycenter.server.zk.IRegistryCenter;
import com.mycode.zookeeper.registerycenter.server.zk.RegistryCenterImpl;

import java.io.IOException;

/**
 * 蛮小江
 * 2018/6/19 21:28
 */
//一个节点机器发布多个服务
public class ServerStart {
    public static void main(String[] args) throws IOException {
        IRegistryCenter registryCenter = new RegistryCenterImpl();
        RpcServer server = new RpcServer("127.0.0.1:8000", registryCenter);
        IHello hello1 = new HelloServiceImpl();
        IHello hello2 = new HelloServiceImpl2();
        server.bind(hello2,hello1);
        server.publish();
        System.in.read();
    }
}
