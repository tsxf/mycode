package com.mycode.io.netty.example.chap02.serialize;

import com.mycode.io.netty.example.chap02.serialize.impl.JSONSerializer;

/**
 * jf
 * 2018/11/7 16:32
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();
    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     *java 对象转换成二进制
     * @param object
     * @return
     */
    byte[] serializer(Object object);

    /**
     * 二进制转换成 java 对象
     * @param cls
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserializer(Class<T> cls, byte[] bytes);
}
