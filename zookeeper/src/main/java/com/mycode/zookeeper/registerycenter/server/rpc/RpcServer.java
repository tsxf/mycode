package com.mycode.zookeeper.registerycenter.server.rpc;
import com.mycode.zookeeper.registerycenter.server.anno.RpcAnnotation;
import com.mycode.zookeeper.registerycenter.server.zk.IRegistryCenter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 蛮小江
 * 2018/6/19 20:47
 */
public class RpcServer {
    private final static ExecutorService executorService = Executors.newCachedThreadPool();
    //服务发布地址
    private  String serviceAddress;
    //注册中心
    private IRegistryCenter registryCenter;
    //存放服务名称和服务对象之间的关系
    private Map<String,Object> handlerMap = new HashMap<>();

    public RpcServer(String serviceAddress, IRegistryCenter registryCenter) {
        this.serviceAddress = serviceAddress;
        this.registryCenter = registryCenter;
    }

    public void  bind(Object... services){
        for (Object service :services){
            //IHeeloService
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();
            String version = annotation.version();
            if(version != null && !"".equals(version)){
                serviceName =  serviceName + "-"+version;
            }
            handlerMap.put(serviceName, service);
        }
    }

    public  void  publish(){
        //先注册服务
        for (String interfaceName : handlerMap.keySet()) {
            registryCenter.registry(interfaceName,serviceAddress);
            System.out.println("注册服务成功:"+interfaceName+"->"+serviceAddress);
        }
        ServerSocket serverSocket = null;
        String[] addrs = serviceAddress.split(":");
        try {
            serverSocket = new ServerSocket(Integer.parseInt(addrs[1]));

            //循环监听客户端连接，任务提交到线程池中
            while(true){
                Socket socket = serverSocket.accept();
                //通过线程池去处理请求
                executorService.submit(new ProcessHandler(socket,handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
