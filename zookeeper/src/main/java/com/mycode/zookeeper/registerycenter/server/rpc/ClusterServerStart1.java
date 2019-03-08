package com.mycode.zookeeper.registerycenter.server.rpc;

import com.mycode.zookeeper.registerycenter.server.zk.IRegistryCenter;
import com.mycode.zookeeper.registerycenter.server.zk.RegistryCenterImpl;

import java.io.IOException;

/**
 * 蛮小江
 * 2018/6/19 21:28
 */
//集群1
public class ClusterServerStart1 {
    public static void main(String[] args) throws IOException {
        IRegistryCenter registryCenter = new RegistryCenterImpl();
        RpcServer server = new RpcServer("127.0.0.1:8000", registryCenter);
        IHello hello1 = new HelloServiceImpl();
        server.bind(hello1);
        server.publish();
        System.in.read();
    }
}
