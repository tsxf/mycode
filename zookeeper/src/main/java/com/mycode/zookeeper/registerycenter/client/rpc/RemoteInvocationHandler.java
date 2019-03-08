package com.mycode.zookeeper.registerycenter.client.rpc;




import com.mycode.zookeeper.registerycenter.client.zk.IServiceDiscovery;
import com.mycode.zookeeper.registerycenter.server.rpc.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 蛮小江
 * 2018/6/19 14:06
 */
public class RemoteInvocationHandler implements InvocationHandler {
  private IServiceDiscovery serviceDiscovery;
  private String version;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery, String version) {
        this.serviceDiscovery = serviceDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //组装请求
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParamters(args);
        request.setVersioni(version);

        //根据接口名称，从注册中心获取对应的地址
        final String serviceAddress = serviceDiscovery.discovery(request.getClassName());

        //通过TCP协议进行传输
        TCPTransport transport = new TCPTransport(serviceAddress);
        //发送请求
        return  transport.send(request);
    }
}
