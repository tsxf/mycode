package com.mycode.framework.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 江富 on 2018/4/30
 */
//专人干专事
public class HandlerAdapter {
    private Map<String, Integer> paramsMap = new HashMap<String, Integer>();

    public HandlerAdapter(Map<String, Integer> paramsMap) {
        this.paramsMap = paramsMap;
    }

    /**
     * 为什么要把handler传过来，因为handler中包含了Controller，method,url信息
     *
     * @param request
     * @param response
     * @param handlerMapping
     * @return
     * @throws Exception
     */
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, HandlerMapping handlerMapping) throws Exception {
        //根据用户请求的参数信息，跟method中的参数信息进行动态匹配
        //resp传进来的目的是只有一个：只是为了将其赋值给方法参数，仅此而已

        //只有当用户传过来的ModelAndView为空的时候，参会new一个默认的

        //1. 要准备好这个方法的形参列表
        //方法重载，形参的决定因素：参数的个数，参数的类型，参数顺序，方法的名字
        Class<?>[] parameterTypes = handlerMapping.getMethod().getParameterTypes();


        //2.拿到自定义命名参数所在的位置
        Map<String, String[]> requestParameterMap = request.getParameterMap();


        //3. 构造实参列表
        Object[] paramValues = new Object[parameterTypes.length];

        for (Map.Entry<String, String[]> param:requestParameterMap.entrySet()){

            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll("\\s", "");
            if(!paramsMap.containsKey(param.getKey())){
                continue;
            }
            Integer index = this.paramsMap.get(param.getKey());
            paramValues[index]=castStringValue(value,parameterTypes[index]);
        }

        if(this.paramsMap.containsKey(HttpServletRequest.class.getName())){
            int reqIndex = this.paramsMap.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        if(this.paramsMap.containsKey(HttpServletResponse.class.getName())){
            int respIndex = this.paramsMap.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }

        //4.从handler中取出controller，method 利用反射机制进行调用
        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController(), paramValues);
        if (result == null) {
            return null;
        }

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == ModelAndView.class;
        if (isModelAndView) {
            return (ModelAndView) result;
        }
        return null;
    }

    private Object castStringValue(String value, Class<?> clazz) {
        if(clazz ==String.class){
            return  value;
        }else if(clazz == Integer.class){
            return Integer.valueOf(value);
        }else if(clazz == int.class){
            return  Integer.valueOf(value).intValue();
        }
        return null;
    }
}
