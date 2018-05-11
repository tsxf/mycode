package com.mycode.framework.context.support;

import com.mycode.framework.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by 蛮小江 on 2018/4/25
 */
//用于对配置文件，进行查找，读取和解析
public class BeanDefinitionReader {
    //用properties代替xml,加载配置文件对象
    private Properties config = new Properties();
    //存储beanClass到List中
    private List<String> registyBeanCLass = new ArrayList<String>();
    //在配置文件中，用来获取自动扫描的包名字key
    private final String SCAN_PACKAGE = "scanPackage";

    //获取注册的beanClassName
    public List<String> loadBeanDefinitions() {
        return registyBeanCLass;
    }

    //获取properties配置对象
    public Properties getConfig() {
        return this.config;
    }


    public BeanDefinitionReader(String... locations) {
        //在Spring中是通过Reader去查找和定位对不对
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            config.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //扫描classpath里面配置文件制定包下面的beanNameClass注册到registyBeanCLass这个List中
        doScanneer(config.getProperty(SCAN_PACKAGE));

    }

    //通过递归把所有相关联的class，并且保存到一个list中
    private void doScanneer(String packageName) {
        //把所有的.替换为空，前面加上路径标示，通过这个url统一路径标识符，去判断这个路径是否是文件，不是递归，是的话放入List中
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));

        File classFile = new File(url.getFile());

        for (File file : classFile.listFiles()) {
            if (file.isDirectory()) {
                //递归调用，参数是package,由原来的的父目录加入现在的子文件名字
                doScanneer(packageName + "." + file.getName());
            } else {
                //通过判断是否是文件加入的List中，值是当前的packageName拼接file的名字，同时把文件后缀去掉
                registyBeanCLass.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }

    }


    //字符串首字母转换为小写
    private String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


    /**
     * 每注册一个className,就返回一个BeanDefinition,只为了对配置信息进行包装
     *
     * @param className
     * @return
     */
    public BeanDefinition registerBean(String className) {
        if (this.registyBeanCLass.contains(className)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            //FactoryBeanName 取得是className全路径文件名字
            beanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));
            return beanDefinition;
        }
        return null;
    }


}

