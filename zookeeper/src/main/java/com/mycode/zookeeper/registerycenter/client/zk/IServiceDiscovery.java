package com.mycode.zookeeper.registerycenter.client.zk;

/**
 * 蛮小江
 * 2018/6/19 21:45
 */
public interface IServiceDiscovery {
    /**
     * 根据请求的服务名称，获取服务的地址
     * @param serviceName
     * @return
     */
    String discovery(String serviceName);
}
