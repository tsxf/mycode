package com.mycode.zookeeper.registerycenter.server.rpc;

import com.mycode.zookeeper.registerycenter.server.zk.IRegistryCenter;
import com.mycode.zookeeper.registerycenter.server.zk.RegistryCenterImpl;

import java.io.IOException;

/**
 * 蛮小江
 * 2018/6/19 21:28
 */
//集群2
public class ClusterServerStart2 {
    public static void main(String[] args) throws IOException {
        IRegistryCenter registryCenter = new RegistryCenterImpl();
        RpcServer server = new RpcServer("127.0.0.1:8001", registryCenter);
        IHello hello = new HelloServiceImpl2();
        server.bind(hello);
        server.publish();
        System.in.read();
    }
}
