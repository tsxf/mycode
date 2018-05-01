package com.gupaoedu.vip.demo.service.impl;

import com.gupaoedu.vip.demo.service.IDemoService;
import com.gupaoedu.vip.spring.annotation.Service;

/**
 * Created by 江富 on 2018/4/22
 */
@Service
public class DemoService implements IDemoService{
    @Override
    public String get(String name) {
        return "My name is :"+name;
    }
}
