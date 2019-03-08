package com.mycode.zookeeper.registerycenter.server.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 蛮小江
 * 2018/6/19 20:30
 */
public class RegistryCenterImpl implements IRegistryCenter {
    private CuratorFramework curatorFramework;

    public RegistryCenterImpl() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZKConfig.CONNECTION_STR)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();
    }

    @Override
    public void registry(String serviceName, String serviceAddress) {
        try {
            String servicePath = ZKConfig.ZK_REGISTRY_NODE + "/" + serviceName;
            //registries/product-service
            Stat stat = curatorFramework.checkExists().forPath(servicePath);
            if(stat==null){
                //不存在创建这个节点
                curatorFramework.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath,"0".getBytes());
            }
             //registries/product-service/ip:port
            String addressPath = servicePath+"/"+serviceAddress;
            String nodePath = curatorFramework.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(addressPath, "0".getBytes());
            System.out.println("服务注册成功:"+nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
