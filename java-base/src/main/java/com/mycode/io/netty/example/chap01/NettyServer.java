package com.mycode.io.netty.example.chap01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author 闪电侠
 */
public class NettyServer {
    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //服务引导类
        serverBootstrap
                //绑定boss，worker线程组, 指定线程模型
                .group(boss, worker)
                //设置IO模型（非阻塞）
                .channel(NioServerSocketChannel.class)
                //每一条建立成功后连接的SocketChannel后续可以做的事情
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println("socket channel  init ......,key:" + ch.attr(AttributeKey.valueOf("clientKey")));
                       // ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });

         /* .attr(AttributeKey.newInstance("nettyName"),"nettyServer")
                .bind(8000);*/

         //设置ServerSocketChannel属性
        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");
        //设置每一条连接的属性
        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        //设置ServerSocketChannel 选项
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        //设置SocketChannel ,每一条连接的选项
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        //服务端在启动中可以做的事情
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {

            @Override
            protected void initChannel(NioServerSocketChannel ctx) throws Exception {
                System.out.println("服务端启动中.......,serverName:" + ctx.attr(AttributeKey.valueOf("serverName")));
            }
        });


        //异步启动NettyServer
        bind(serverBootstrap, 3307);

    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("服务启动成功！，端口：" + port);
                } else {
                    System.out.println("启动失败，端口：" + port);
                    bind(serverBootstrap, port + 1);
                }
            }
        });

    }

}





