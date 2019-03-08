package com.mycode.zookeeper.registerycenter.client.rpc;

import com.mycode.zookeeper.registerycenter.client.zk.IServiceDiscovery;

import java.lang.reflect.Proxy;

/**
 * 蛮小江
 * 2018/6/19 22:36
 */
public class RPCClientProxy {
    private IServiceDiscovery serviceDiscovery;

    public RPCClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
    public <T> T newProxy(final Class<T> interfaceCls,String version){
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls}, new RemoteInvocationHandler(serviceDiscovery, version));
    }
}
