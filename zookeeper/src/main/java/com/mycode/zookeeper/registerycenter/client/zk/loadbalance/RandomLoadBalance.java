package com.mycode.zookeeper.registerycenter.client.zk.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * 蛮小江
 * 2018/6/19 22:00
 */
public class RandomLoadBalance extends  AbstractLoadBalance {
    @Override
    protected String  doSelect(List<String> list) {
        int length =list.size();
        Random random = new Random();
        return list.get(random.nextInt(length));
    }
}
