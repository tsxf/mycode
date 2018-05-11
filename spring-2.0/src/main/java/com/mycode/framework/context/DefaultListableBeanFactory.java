package com.mycode.framework.context;

import com.mycode.framework.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 蛮小江
 * 2018/5/10 0:01
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext {

    //beanDefinitioinMap用来存放配置信息
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    @Override
    protected void onRefresh() {
        super.onRefresh();
    }

    @Override
    protected void refreshBeanFactory() {

    }
}
