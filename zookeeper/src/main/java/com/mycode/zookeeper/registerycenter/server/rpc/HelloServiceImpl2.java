package com.mycode.zookeeper.registerycenter.server.rpc;

import com.mycode.zookeeper.registerycenter.server.anno.RpcAnnotation;

/**
 * 蛮小江
 * 2018/6/19 20:45
 */
@RpcAnnotation(value = IHello.class,version = "2.0")
public class HelloServiceImpl2 implements IHello {
    @Override
    public String sayHello(String name) {
          return "hello "+name+",I'm 8001 node";
    }
}
