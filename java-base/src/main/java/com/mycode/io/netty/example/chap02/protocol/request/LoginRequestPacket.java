package com.mycode.io.netty.example.chap02.protocol.request;

import com.mycode.io.netty.example.chap02.protocol.packet.Packet;
import lombok.Data;

import static com.mycode.io.netty.example.chap02.protocol.command.Command.LOGIN_REQUEST;

/**
 * jf
 * 2018/11/7 16:54
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
