package com.mycode.zookeeper.registerycenter.client.zk;

import com.mycode.zookeeper.registerycenter.client.zk.loadbalance.LoadBalance;
import com.mycode.zookeeper.registerycenter.client.zk.loadbalance.RandomLoadBalance;
import com.mycode.zookeeper.registerycenter.server.zk.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * 蛮小江
 * 2018/6/19 21:47
 */
public class ServiceDiscoveryImpl implements  IServiceDiscovery {
    private CuratorFramework curatorFramework;
    private List<String> nodelist = new ArrayList<>();
    public ServiceDiscoveryImpl(String address) {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();
    }

    @Override
    public String discovery(String serviceName) {
        String path = ZKConfig.ZK_REGISTRY_NODE+"/"+serviceName;
        try {
            nodelist=  curatorFramework.getChildren().forPath(path);
            //注册监听事件
            registeryWatcher(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //负载均衡
        LoadBalance balance = new RandomLoadBalance();
        return balance.selectHost(nodelist);
    }

    private void registeryWatcher(String path) {
        PathChildrenCache cache = new PathChildrenCache(curatorFramework,path,true);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                //获取最新的节点下面的子节点信息
                nodelist=  curatorFramework.getChildren().forPath(path);
            }
        });
        try {
            cache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
