package com.mycode.rmi.rpc.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 蛮小江
 * 2018/6/19 8:42
 */
public class RpcServer {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    public  void publish(Object service,int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                //一个连接对应一个服务处理
                Socket socket = serverSocket.accept();
                System.out.println("发布服务成功......");
                executorService.submit(new ProcessHandler(socket, service));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
