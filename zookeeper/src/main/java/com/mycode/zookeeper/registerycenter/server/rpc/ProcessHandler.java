package com.mycode.zookeeper.registerycenter.server.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * 蛮小江
 * 2018/6/19 20:59
 */
public class ProcessHandler implements Runnable {
    private Socket socket;
    private Map<String, Object> handlerMap;

    public ProcessHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }


    public Object invoke(RpcRequest request) throws Throwable {
        Object[] paramters = request.getParamters();

        Class<?> [] types = new Class[paramters.length];
        for (int i = 0; i<paramters.length ;i++){
            types[i]=paramters[i].getClass();
        }

        String serviceName = request.getClassName();
        String version = request.getVersioni();
        if(version != null && !"".equals(version)){
            serviceName =  serviceName + "-"+version;
        }

        //从handlerMap中，根据客户端请求的地址，去拿到响应的服务，通过反射发起调用
        Object service = handlerMap.get(serviceName);
        Method method = service.getClass().getMethod(request.getMethodName(), types);
        return method.invoke(service,paramters);
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        RpcRequest request = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            request = (RpcRequest) inputStream.readObject();

            Object result = invoke(request);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();

            //关闭流信息
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
