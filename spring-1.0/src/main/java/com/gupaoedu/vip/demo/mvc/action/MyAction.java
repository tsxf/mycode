package com.gupaoedu.vip.demo.mvc.action;

import com.gupaoedu.vip.demo.service.IDemoService;
import com.gupaoedu.vip.spring.annotation.Autowired;
import com.gupaoedu.vip.spring.annotation.Controller;
import com.gupaoedu.vip.spring.annotation.RequestMapping;

/**
 * Created by 江富 on 2018/4/22
 */
@Controller
public class MyAction {
    @Autowired
    private IDemoService service;

    @RequestMapping("/index.html")
    public void querry() {

    }
}
