package com.mycode.demo.aspect;

/**
 * 蛮小江
 * 2018/5/9 23:49
 */
//切面增强
public class LogAspect {
    /**
     * 实现的功能增强，方法调用之前
     */
    public void before(){
        System.out.println("========= before A.....===============");
    }

    /**
     * 实现的功能增强，方法调用之后
     */
    public void after(){
        System.out.println("========= after B.....===============");
    }


}
