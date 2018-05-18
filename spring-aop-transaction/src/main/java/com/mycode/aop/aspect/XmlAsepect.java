package com.mycode.aop.aspect;


import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.aopalliance.intercept.Joinpoint;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.sound.midi.Soundbank;

/**
 * 蛮小江
 * 2018/5/13 16:48
 */
//XML版Aspect切面Bean（理解为TransactionManager）


public class XmlAsepect {
    private final static Logger logger = Logger.getLogger(XmlAsepect.class);

    /**
     * 配置前置通知，使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象，可以没有改参数
     */
    public void before(JoinPoint joinPoint) {

        /*System.out.println(joinPoint.getArgs());  //获取实参列表
        System.out.println(joinPoint.getKind());//获取连接点类型，如method-execution;
        System.out.println(joinPoint.getTarget());//获取目标对象
        System.out.println(joinPoint.getSignature());//获取被调用的切点
        System.out.println(joinPoint.getThis());//获取this对象*/

       // System.out.println("=============================");
        logger.info("before:"+joinPoint);
    }

    /**
     * 配置后置通知，使用方式在asepect()上注册的切入点
     * 相当于是finally，不管是否有异常，最终都会执行
     */
    public void after(JoinPoint joinPoint) {
        logger.info("after:"+joinPoint);
    }


    /**
     * 环绕通知
     * @param joinpoint
     */
    public void around(JoinPoint joinpoint) {
        long start =System.currentTimeMillis();
        try {
            Object proceed = ((ProceedingJoinPoint)joinpoint).proceed();//ProceedingJoinPoint
            long end =System.currentTimeMillis();
            logger.info("around " + joinpoint + "\tUse time : " + (end - start) + " ms!");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            logger.info("around " + joinpoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
        }
    }

    /**
     * 返回通知
     */
    public void afterReturn(JoinPoint joinpoint) {
        logger.info("afterReturn:"+joinpoint);
    }

    /**
     * 抛出异常后通知
     * @param joinpoint
     */
    public void afterThrow(JoinPoint joinpoint,Exception e) {
        logger.info("afterReturn:"+joinpoint+"\t"+e.getMessage());
    }
}
