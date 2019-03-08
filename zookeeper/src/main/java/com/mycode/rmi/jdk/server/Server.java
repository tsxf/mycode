package com.mycode.rmi.jdk.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 蛮小江
 * 2018/6/19 8:31
 */
public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        IHelloService service = new HelloServiceImpl();
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1/IHello",service);
        System.out.println("服务发布成功");
    }
}
