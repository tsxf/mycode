package com.mycode.io.netty.example.chap01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * jf
 * 2018/11/7 12:38
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println("handle 激活成功......");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString(Charset.forName("UTF-8")));

        System.out.println(new Date()+"服务端会写数据：");

        ByteBuf byteBuf = getByteBuffer(ctx);
        ctx.channel().writeAndFlush(byteBuf);

    }

    private ByteBuf getByteBuffer(ChannelHandlerContext ctx) {

        byte[] bytes = "你好客户端，我是服务端，很高兴认识你：".getBytes(Charset.forName("UTF-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        return buffer.writeBytes(bytes);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
           e.printStackTrace();
           ctx.close();
    }
}
