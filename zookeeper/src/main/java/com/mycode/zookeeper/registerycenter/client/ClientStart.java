package com.mycode.zookeeper.registerycenter.client;

import com.mycode.zookeeper.registerycenter.client.rpc.RPCClientProxy;
import com.mycode.zookeeper.registerycenter.client.zk.IServiceDiscovery;
import com.mycode.zookeeper.registerycenter.client.zk.ServiceDiscoveryImpl;
import com.mycode.zookeeper.registerycenter.server.rpc.IHello;
import com.mycode.zookeeper.registerycenter.server.zk.ZKConfig;

/**
 * 蛮小江
 * 2018/6/19 22:55
 */
public class ClientStart {
    public static void main(String[] args) throws InterruptedException {
        IServiceDiscovery discovery = new ServiceDiscoveryImpl(ZKConfig.CONNECTION_STR);
        RPCClientProxy proxy = new RPCClientProxy(discovery);
        for (int i = 0;i <10;i++){
            IHello hello = proxy.newProxy(IHello.class, null);
            String result = hello.sayHello("neo");
            System.out.println(result);
            Thread.sleep(1000);
        }


    }
}
