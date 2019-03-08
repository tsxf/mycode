package com.mycode.zookeeper.registerycenter.client.zk.loadbalance;

import java.util.List;

/**
 * 蛮小江
 * 2018/6/19 21:57
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectHost(List<String> list) {
        if(list == null || list.size()==0){
            return  null;
        }
        if(list.size()==1){
            return list.get(0);
        }

        return doSelect(list);
    }

    protected abstract String  doSelect(List<String> list);

}
