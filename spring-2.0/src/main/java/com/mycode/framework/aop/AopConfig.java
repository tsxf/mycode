package com.mycode.framework.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 蛮小江
 * 2018/5/9 23:51
 */
//对配置文件中关于aspect的解析，如同对scanPackage解析成BeanDefinitiion,template解析成ViewResolver
public class AopConfig {
    //切面，切点，增强的方法

    private Map<Method, Aspect> points = new HashMap<Method, Aspect>();

    public Aspect get(Method method){
        return points.get(method);
    }

    public  void put(Method method,Object aspect,Method [] methods){
        points.put(method, new Aspect(aspect,methods));
    }


    public boolean contains(Method method){
        return points.containsKey(method);
    }


    //对增强的封装
    class Aspect{
        //切面对象，这里是日志记录LogAspect
        private  Object aspect;
        //切面里面的增强方法，before,after
        private Method []  methods;

        //通过函数，初始化切面和增强
        public Aspect(Object aspect, Method[] methods) {
            this.aspect = aspect;
            this.methods = methods;
        }

        public Object getAspect() {
            return aspect;
        }

        public Method[] getMethods() {
            return methods;
        }
    }


}


