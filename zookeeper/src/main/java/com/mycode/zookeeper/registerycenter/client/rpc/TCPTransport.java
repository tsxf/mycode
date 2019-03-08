package com.mycode.zookeeper.registerycenter.client.rpc;

import com.mycode.zookeeper.registerycenter.server.rpc.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 蛮小江
 * 2018/6/19 22:40
 */
public class TCPTransport {
    private String serviceAddress;

    public TCPTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private Socket newSocket(){
        String[] addrs = serviceAddress.split(":");
        try {
            System.out.println("创建一个新的连接");
            Socket socket = new Socket(addrs[0], Integer.parseInt(addrs[1]));
            return  socket;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("建立连接失败");

        }

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

            inputStream.close();
            outputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("发起远程调用异常,", e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }

        }
        return result;
    }
}
