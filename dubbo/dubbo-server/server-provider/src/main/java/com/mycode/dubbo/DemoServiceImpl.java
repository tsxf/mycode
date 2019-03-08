package com.mycode.dubbo;

import com.mycode.dubbo.api.IDemoService;

/**
 * 蛮小江
 * 2018/6/20 19:30
 */
public class DemoServiceImpl implements IDemoService {
    @Override
    public String protocolDemo(String msg) {
        return "hello ===========>"+msg;
    }
}
