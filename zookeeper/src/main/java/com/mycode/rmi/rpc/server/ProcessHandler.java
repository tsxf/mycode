package com.mycode.rmi.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 蛮小江
 * 2018/6/19 8:46
 */
public class ProcessHandler implements Runnable {
    //客户端连接对应的socket
    private Socket socket;
    //发布的服务
    private Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream output = null;
        try {
            //获取客户端输入流信息
            inputStream = new ObjectInputStream((socket.getInputStream()));
            RpcRequest request = (RpcRequest) inputStream.readObject();

            //反射调用请求参数
            Object result = invoke(request);

            //把响应内容写回去
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(result);
            output.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {

                if (inputStream != null) {
                    inputStream.close();
                }

                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object invoke(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //通过反射的方式调用请求，通过seervice服务，获取请求的对应的method对象，根据参数反射调用对象
        String methodName = request.getMethodName();
        Object[] paramters = request.getParamters();
        Class<?>[] types = new Class[paramters.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = paramters[i].getClass();
        }
        Method method = service.getClass().getMethod(methodName, types);
        return method.invoke(service,paramters);
    }
}
