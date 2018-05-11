package com.mycode.framework.webmvc.servlet;

import java.util.Map;

/**
 * 模型视图
 * Created by 蛮小江 on 2018/4/30
 */
public class ModelAndView {
    private  String viewName;
    private Map<String,?> model;

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
