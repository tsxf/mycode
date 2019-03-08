package com.mycode.io.netty.example.chap02.protocol.packet;

/**
 * jf
 * 2018/11/7 16:48
 */

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public abstract class  Packet {
    /**
     * 协议版本
     */

    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
