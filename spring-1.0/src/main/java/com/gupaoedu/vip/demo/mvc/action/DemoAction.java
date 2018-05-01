package com.gupaoedu.vip.demo.mvc.action;

import com.gupaoedu.vip.demo.service.IDemoService;
import com.gupaoedu.vip.spring.annotation.Autowired;
import com.gupaoedu.vip.spring.annotation.Controller;
import com.gupaoedu.vip.spring.annotation.RequestMapping;
import com.gupaoedu.vip.spring.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 江富 on 2018/4/22
 */
@Controller
@RequestMapping("/demo")
public class DemoAction {
    @Autowired
    private IDemoService service;

    @RequestMapping("/querry.json")
    public void querry(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name) throws Exception {
        String result = service.get(name);
        System.out.println(result);
        response.getWriter().write(result);
    }

    @RequestMapping("/edit.json")
    public  void edit(HttpServletRequest request,HttpServletResponse response,Integer id){

    }
}
