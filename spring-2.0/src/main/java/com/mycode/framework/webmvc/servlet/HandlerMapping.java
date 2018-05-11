package com.mycode.framework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**对请求的url映射contorller，封装成对象，支持正则匹配
 * Created by 蛮小江 on 2018/4/30
 */
public class HandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern;//对url的封装

    public HandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }


}
