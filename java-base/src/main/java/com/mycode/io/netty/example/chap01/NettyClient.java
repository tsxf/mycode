package com.mycode.io.netty.example.chap01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 闪电侠
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                       // ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });



        //NioSocketChannel 自身绑定属性
        bootstrap.attr(AttributeKey.newInstance("clientId"),"clientValue");

        //设置连接 TCP的一些属性
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);


        connect(bootstrap,"127.0.0.1",3307,5);

   /*     Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }*/
    }

    private final static int MAX_RETRY = 5;

    private static void connect(Bootstrap bootstrap, final String ip, final int port, final int retry) {

        bootstrap.connect(ip, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("建立连接成功");
                } else {
                    //第几次重连
                    int order = (MAX_RETRY - retry) + 1;
                    //本次重连的间隔
                    int delay = 1 << order;
                    System.out.println(new Date() + "连接失败，第：" + order + "次重试");
                    bootstrap.config().group().schedule(new Runnable() {
                        @Override
                        public void run() {
                            connect(bootstrap, ip, port, retry - 1);
                        }
                    },delay, TimeUnit.SECONDS);
                }
            }
        });
    }
}
