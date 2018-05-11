package com.mycode.framework.core;

/**
 * Created by 蛮小江 on 2018/4/25
 */
public interface BeanFactory {
    /**
     * 根据beanName从IOC容器中获取一个实例Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
