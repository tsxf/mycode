package com.mycode.rmi.jdk.client;

import com.mycode.rmi.jdk.server.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 蛮小江
 * 2018/6/19 8:35
 */
public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        IHelloService service = (IHelloService)Naming.lookup("rmi://127.0.0.1/IHello");
        String result = service.sayHello("Neo");
        System.out.println(result);
    }
}
