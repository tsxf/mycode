package com.mycode.zookeeper.registerycenter.server.zk;

/**
 * 蛮小江
 * 2018/6/19 20:28
 */
public interface IRegistryCenter {
    /**
     * 注册服务名称和服务地址
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName,String serviceAddress);
}
