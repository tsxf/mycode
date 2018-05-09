package com.mycode.demo.action;

import com.mycode.demo.service.IQuerryService;
import com.mycode.framework.annotation.Autowired;
import com.mycode.framework.annotation.Controller;
import com.mycode.framework.annotation.RequestMapping;
import com.mycode.framework.annotation.RequestParam;
import com.mycode.framework.webmvc.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 江富 on 2018/4/29
 */
@Controller
@RequestMapping("/")
public class PageAction {
    @Autowired
    private IQuerryService  querryService;

    @RequestMapping("first.html")
    public ModelAndView querry(@RequestParam("name") String name){
        String result = querryService.querry(name);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("name",name);
        model.put("data",result);
        model.put("token","123456");
        return new ModelAndView("first.html", model);

    }
}
