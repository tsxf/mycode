package com.mycode.io.netty.example.chap01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * jf
 * 2018/11/7 12:26
 */

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接建立成功后，发送消息
        System.out.println(new Date() + ": 客户端写出数据");
        ByteBuf byteBuf = getByteBuffer(ctx);
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuffer(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，我是客户端".getBytes(Charset.forName("UTF-8"));
        ByteBuf buffer = ctx.alloc().buffer();
         buffer.writeBytes(bytes);
         return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("读取服务端数据："+buf.toString(Charset.forName("UTF-8")));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        ctx.close();
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
}
