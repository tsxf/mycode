package com.mycode.io.netty.example.chap02.protocol.respoonse;

import com.mycode.io.netty.example.chap02.protocol.packet.Packet;
import lombok.Data;

import static com.mycode.io.netty.example.chap02.protocol.command.Command.LOGIN_RESPONSE;

/**
 * jf
 * 2018/11/8 10:32
 */
@Data
public class LoginResponsePacket extends Packet {
    //登录成功与否的标志位
    private  Boolean success;
    //失败理由
    private  String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
