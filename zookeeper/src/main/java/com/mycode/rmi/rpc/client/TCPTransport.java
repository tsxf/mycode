package com.mycode.rmi.rpc.client;

import com.mycode.rmi.rpc.server.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 蛮小江
 * 2018/6/19 14:07
 */
public class TCPTransport {
    private String host;
    private int port;

    public TCPTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket() {
        Socket socket = null;

        try {
            socket = socket = new Socket(host, port);
            System.out.println("创建一个新的连接");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("建立连接失败");
            throw new RuntimeException("建立连接失败");
        }
        return socket;
    }

    public Object send(RpcRequest request) throws IOException {
        Socket socket = newSocket();
        Object result = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            //读取响应
            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("发起远程调用异常,", e);
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return result;
    }
}
