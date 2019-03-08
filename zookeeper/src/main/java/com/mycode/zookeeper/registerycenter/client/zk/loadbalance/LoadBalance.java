package com.mycode.zookeeper.registerycenter.client.zk.loadbalance;

import java.util.List;

/**
 * 蛮小江
 * 2018/6/19 21:56
 */
public interface LoadBalance {
    String selectHost(List<String > list);
}
