package com.mycode.io.netty.example.chap02.protocol.command;

/**
 * jf
 * 2018/11/7 16:47
 */
public interface Command {
    //登录请求指令
   byte LOGIN_REQUEST =1;
   //登录响应指令
   byte LOGIN_RESPONSE =2;
}
