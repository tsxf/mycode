package com.mycode.demo.action;

import com.mycode.demo.service.IModifyService;
import com.mycode.demo.service.IQuerryService;
import com.mycode.framework.annotation.Autowired;
import com.mycode.framework.annotation.Controller;
import com.mycode.framework.annotation.RequestMapping;
import com.mycode.framework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 蛮小江 on 2018/4/29
 */
@Controller
@RequestMapping("/web")
public class MyAction {
    @Autowired
    private IQuerryService querry;
    @Autowired
    private IModifyService modifyService;

    @RequestMapping("/select.json")
    public void select(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name) {
        String querry = this.querry.querry(name);
        System.out.println(querry);
        out(response,querry);
    }

    @RequestMapping("/edit.json")
    public void add(HttpServletRequest request, HttpServletResponse response, @RequestParam("name") String name, @RequestParam("addr") String addr) {
        String result = modifyService.add(name, addr);
        System.out.println(result);
        out(response,result);
    }


    @RequestMapping("/remove.json")
    public void remove(HttpServletRequest request,HttpServletResponse response,@RequestParam("id") Integer id){
        String result = modifyService.remove(id);
        System.out.println(result);
        out(response,result);
    }

    @RequestMapping("/edit.json")
    public  void edit(HttpServletRequest request,HttpServletResponse response,@RequestParam("id") Integer id,@RequestParam("name") String name) {
        String result = modifyService.edit(id, name);
        out(response,result);

    }

    private  void out (HttpServletResponse response ,String str){
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
