package com.mycode.rmi.jdk.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 蛮小江
 * 2018/6/19 8:28
 */
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {
    protected HelloServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String name) {
        return "IHello "+name;
    }
}
