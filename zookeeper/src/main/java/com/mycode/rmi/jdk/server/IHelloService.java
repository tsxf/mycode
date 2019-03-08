package com.mycode.rmi.jdk.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 蛮小江
 * 2018/6/19 8:27
 */
public interface IHelloService extends Remote {
    String sayHello(String name) throws RemoteException;
}
