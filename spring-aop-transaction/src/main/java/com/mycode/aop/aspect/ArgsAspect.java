package com.mycode.aop.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 蛮小江
 * 2018/5/13 16:48
 */
//声明这是一个组件
@Component
//声明这是一个切面
@Aspect
public class ArgsAspect {
    private final static Logger logger = Logger.getLogger(ArgsAspect.class);

    @Pointcut("execution(* com.mycode.aop.service..*(..))")
    public void aspect() {
    }

    @Before("execution(com.mycode.model.Member com.mycode.aop.service..*(..))")
    public void beforeReturnUser(JoinPoint joinPoint) {
        logger.info("beforeReturnUser " + joinPoint);
    }

    @Before("execution(* com.mycode.aop.service..*(com.mycode.model.Member))")
    public void beforeArgUser(JoinPoint joinPoint) {
        logger.info("beforeArgUser " + joinPoint);
    }

    //  @Before("execution(* com.mycode.aop.service..*(id))")
    @Before("aspect()&&args(id)")
    public void beforeArgId(JoinPoint joinPoint, long id) {
        logger.info("beforeArgId " + joinPoint + "\tID:" + id);
    }
}
