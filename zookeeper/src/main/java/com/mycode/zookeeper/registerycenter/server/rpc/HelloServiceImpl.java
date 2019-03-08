package com.mycode.zookeeper.registerycenter.server.rpc;

import com.mycode.zookeeper.registerycenter.server.anno.RpcAnnotation;

/**
 * 蛮小江
 * 2018/6/19 20:45
 */
@RpcAnnotation(IHello.class)
public class HelloServiceImpl implements IHello {
    @Override
    public String sayHello(String name) {
        return "hello "+name+",I'm 8000 node";
    }
}
