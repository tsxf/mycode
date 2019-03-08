package com.mycode.io.netty.example.chap02.serialize.impl;


import com.alibaba.fastjson.JSON;
import com.mycode.io.netty.example.chap02.serialize.Serializer;
import com.mycode.io.netty.example.chap02.serialize.SerializerAlogrithm;


/**
 * jf
 * 2018/11/7 16:35
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serializer(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserializer(Class<T> cls, byte[] bytes) {
        return JSON.parseObject(bytes,cls);
    }
}
