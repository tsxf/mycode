package com.mycode.framework.context;

import com.mycode.demo.action.MyAction;
import com.mycode.framework.annotation.Autowired;
import com.mycode.framework.annotation.Controller;
import com.mycode.framework.annotation.Service;
import com.mycode.framework.beans.BeanDefinition;
import com.mycode.framework.beans.BeanPostProcessor;
import com.mycode.framework.beans.BeanWrapper;
import com.mycode.framework.context.support.BeanDefinitionReader;
import com.mycode.framework.core.BeanFactory;
import jdk.internal.org.objectweb.asm.tree.IincInsnNode;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 江富 on 2018/4/25
 */
public class ApplicatioinContext implements BeanFactory {


    //存放配置文件路径
    private String[] configLocations;

    //读取，加载配置文件
    private BeanDefinitionReader reader;

    //beanDefinitioinMap用来存放配置信息
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    //用来保证注册时单例的容器
    private Map<String, Object> beanCacheMap = new HashMap<String, Object>();

    //用来存储所有的被代理过的对象
    private Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, BeanWrapper>();

    public ApplicatioinContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    private void refresh() {
        //定位
        this.reader = new BeanDefinitionReader(this.configLocations);

        //加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();

        //注册
        doRegistry(beanDefinitions);

        //依赖注入(lazy-init=false) ,要执行依赖注入
        doAutowired();

     /*   MyAction myAction = (MyAction) this.getBean("myAction");
        myAction.select(null, null, "哈哈哈哈哈哈");*/


    }

    /**
     * 开始执行自动化的依赖注入
     */
    private void doAutowired() {
        //初始化lazyInit=false，放到beanWrapeperMap中
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                //进行初始化，获取bean对象
                getBean(beanName);
            }
        }

      /*  //类中的属性进行自动注入
        for (Map.Entry<String, BeanWrapper> beanWrapperEntry : this.beanWrapperMap.entrySet()) {

            populalteBean(beanWrapperEntry.getKey(), beanWrapperEntry.getValue().getWrapperInstance());
        }*/
    }

    private void populalteBean(String key, Object instance) {
        Class<?> clazz = instance.getClass();

        //不是所有的牛奶都叫特仑苏
        if (!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
            return;
        }

        //通过反射进行依赖注入
        Field[] fields = clazz.getDeclaredFields();
        for (Field filed : fields) {
            if (!filed.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            Autowired autowired = filed.getAnnotation(Autowired.class);
            String autoWiredBeanName = autowired.value().trim();

            if ("".equals(autoWiredBeanName)) {
                //注解上没有名字，默认用类型的名字
                autoWiredBeanName = filed.getType().getName();
            }

            filed.setAccessible(true);

            try {
                //判断以来的bean是否存在，不存在进行递归实例化和注入
                BeanWrapper dependBeanWrapper = this.beanWrapperMap.get(autoWiredBeanName);
                Object dependInstance = null;
                if (dependBeanWrapper == null) {
                    dependInstance = getBean(autoWiredBeanName);
                } else {
                    dependInstance = dependBeanWrapper.getWrapperInstance();
                }

                System.out.println("=================instance:" + instance + ",autoWiredBeanName:" + autoWiredBeanName + "," + this.beanWrapperMap.get(autoWiredBeanName) + ",dependInstance:" + dependInstance);

                filed.set(instance, dependInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


    private void doRegistry(List<String> beanDefinitions) {
        //beanName 有三种情况
        //1. 默认是首字母小写
        //2. 自定义名字
        //3. 接口注入

        for (String beanClassName : beanDefinitions) {
            try {
                //如果是一个接口，是不能实例化的
                //用它的实现类来实例化
                Class<?> clazz = Class.forName(beanClassName);
                if (clazz.isInterface()) {
                    continue;
                }

                //默认首字母小写进行注册
                BeanDefinition beanDefinition = reader.registerBean(beanClassName);
                if (beanDefinition != null) {
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
                }


                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖
                    //为什么？因为Spring没那么智能，就是这么傻
                    //这个时候，可以自定义名字
                    this.beanDefinitionMap.put(i.getName(), beanDefinition);
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    //依赖注入，从这里开始，读取BeanDefinition中的信息
    //然后通过反射机制创建一个实例并返回
    //Spring做法是：不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1. 保留原来的OOP关系
    //2. 我需要对它进行扩展，增强（为以后AOP打基础）
    @Override
    public Object getBean(String beanName) {

        //如果beanWrapperMap含有这个key，说明已经实例化过，直接在缓存中获取
        if (beanWrapperMap.containsKey(beanName)) {
            return beanWrapperMap.get(beanName).getWrapperInstance();
        }

        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        String className = beanDefinition.getBeanClassName();

        //生成通知事件
        BeanPostProcessor beanPostProcessor = new BeanPostProcessor();

        Object instance = instanionBean(beanDefinition);
        if (null == instance) {
            return null;
        }

        //在实例初始化以前调用一次
        beanPostProcessor.postProcessBeforeInitialization(instance, beanName);

        //进行包装
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        beanWrapper.setPostProcessor(beanPostProcessor);
        this.beanWrapperMap.put(beanName, beanWrapper);

        //依赖注入
        populalteBean(beanName, instance);

        //在实例初始化以后调用一次
        beanPostProcessor.postProcessAfterInitialization(instance, beanName);


        //通过这样一调用，相当于给我们自己留下了可操作的空间
        return this.beanWrapperMap.get(beanName).getWrapperInstance();
    }

    /**
     * 传入一个BeanDefinition,就返回一个实例bean
     *
     * @param beanDefinition
     * @return
     */
    private Object instanionBean(BeanDefinition beanDefinition) {
        Object instance = null;
        String className = beanDefinition.getBeanClassName();

        //因为根据Class才能确定一个类是否有实例
        if (this.beanCacheMap.containsKey(className)) {
            instance = beanCacheMap.get(className);
        } else {
            try {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.beanCacheMap.put(className, instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instance;
    }


    /**
     * 返回beanDefinitionMap存贮的beanClassName全路径
     *
     * @return
     */
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
