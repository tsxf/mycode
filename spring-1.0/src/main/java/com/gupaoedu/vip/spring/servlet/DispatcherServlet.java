package com.gupaoedu.vip.spring.servlet;

import com.gupaoedu.vip.spring.annotation.Autowired;
import com.gupaoedu.vip.spring.annotation.Controller;
import com.gupaoedu.vip.spring.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * Created by 江富 on 2018/4/22
 */
public class DispatcherServlet extends HttpServlet {
    private Properties contextConfig = new Properties();
    private Map<String, Object> beanMap = new HashMap<String, Object>();
    private List<String> classNames = new ArrayList<String>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //定位
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //加载
        doScanner(contextConfig.getProperty("scanPackage"));


        //注册
        doRegistry();


        //依赖注入
        doAutowired();

        //如果是SpringMVC会多设计一个HandlerMapping

        //将@RequestMapping 中配置的url和一个Method关联上
        //以便于从浏览器中获得用户输入的url以后，能够找到具体执行的Method通过发射去调用
        initHandlerMapping();
    }

    private void initHandlerMapping() {

    }

    private void doAutowired() {

        if (beanMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field filed : fields) {
                if (!filed.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

                Autowired autowired = filed.getAnnotation(Autowired.class);

                String beanName = autowired.value().trim();

                if ("".equals(beanName)) {
                    beanName = filed.getType().getName();
                }

                filed.setAccessible(true);

                try {
                    filed.set(entry.getValue(), beanMap.get(beanName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doRegistry() {
        if (classNames.isEmpty()) {
            return;
        }

        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                //在spring中用的多个字方法来处理的
                if (clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = lowerFirstCase(clazz.getSimpleName());

                    //在spring中这个阶段不会直接put instance ,这里put的是BenaDefinitioin
                    beanMap.put(beanName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);

                    //默认使用类名首字母注入
                    //如果定义了beanName,那么优先使用自己定义的beanName
                    //如果是一个接口，使用接口的类型去自动注入

                    //在spring中同样会分别调用不同的方法，autowiredByName,autowiredByType
                    String beanName = service.value();
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }

                    Object instance = clazz.newInstance();

                    beanMap.put(beanName, instance);

                    Class<?>[] interfacess = clazz.getInterfaces();
                    for (Class<?> i : interfacess) {
                        beanMap.put(i.getName(), instance);
                    }
                } else {
                    continue;
                }

            }
        } catch (Exception e) {

        }


    }

    private String lowerFirstCase(String msg) {
        char[] chars = msg.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanner(packageName + "." + file.getName());
            } else {
                classNames.add(packageName + "." + file.getName().replace(".class", ""));
            }

        }

    }

    private void doLoadConfig(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath:",""));

        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
