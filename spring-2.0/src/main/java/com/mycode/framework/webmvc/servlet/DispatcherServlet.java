package com.mycode.framework.webmvc.servlet;

import com.mycode.framework.annotation.Controller;
import com.mycode.framework.annotation.RequestMapping;
import com.mycode.framework.annotation.RequestParam;
import com.mycode.framework.context.ApplicatioinContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Servlet作为一个MVC启动的如入口，在spring中是通过ApplicationContextAware回调
 * Created by 江富 on 2018/4/25
 */


public class DispatcherServlet extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATIONI = "contextConfigLocation";

    //HandlerMappping是最核心的设计，也是最经典的
    //它牛B到直接干掉了Struts,WebWork等框架
    private List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();

    //方法适配参数
    private Map<HandlerMapping, HandlerAdapter> handlerAdapter = new HashMap<HandlerMapping, HandlerAdapter>();

    //视图解析
    private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //处理页面请求，分发调度，返回内容响应

        try{
             doDispatch(req,resp);
        }catch (Exception e){
            //异常栈输出到页面
            StringBuilder sb = new StringBuilder();
            sb.append( "<font size= '50' color='blue'>500 Exception </font><br/>");
            sb.append( "Details:<br/>");
            sb.append( Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]","").replaceAll("\\s","\r\n"));
            sb.append( "<font color='green'><i>Copyright@黑夜的眼睛</font>");
            resp.getWriter().write(sb.toString());
            e.printStackTrace();
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        
        //根据用户请求的url来获得一个handler
        HandlerMapping handlerMapping = getHanler(req);
        if(handlerMapping == null){
            //错误提示
            resp.getWriter().write("<font size='25' color='red'>404 Not Fount</font></br><font color='green'><i>Copyright@黑夜的眼睛</font>");
            return;
        }

        //handler适配
        HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);

        //这一步只是调用方法，通过反射得到返回值
        ModelAndView modelAndView = handlerAdapter.handle(req, resp, handlerMapping);


        //这里才是真正的输出
        processDispatchResult(resp,modelAndView);
    }

    private void processDispatchResult(HttpServletResponse resp, ModelAndView modelAndView) throws Exception {
        //调用viewResolver的resolverView方法
        if(modelAndView == null){
            return;
        }
        if(this.viewResolvers.isEmpty()){
            return;
        }
        for (ViewResolver viewResolver : viewResolvers) {
            //判断是否含有传过来的modelAndView
            if(!modelAndView.getViewName().equals(viewResolver.getViewName())){
                continue;
            }

            //通过视图解析器把页面请求地址转换到项目路径文件中，同时替换里面的表达式，返回一个字符串，再响应给页面
            String content = viewResolver.viewResolver(modelAndView);
            if(content!=null){
                resp.getWriter().write(content);
                break;
            }


        }
    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handlerMapping) {
        if(handlerAdapter.isEmpty()){
            return  null;
        }
        return  handlerAdapter.get(handlerMapping);
    }

    private HandlerMapping getHanler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()){
            return  null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");

        for(HandlerMapping handlerMapping :handlerMappings){
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if(!matcher.matches()){
                continue;
            }
            return  handlerMapping;
        }
        return  null;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     *  启动容器，初始化9大组件
     */
    public void init(ServletConfig config) throws ServletException {
        ApplicatioinContext context = new ApplicatioinContext(config.getInitParameter(CONTEXT_CONFIG_LOCATIONI));
        initStrategies(context);


    }

    private void initStrategies(ApplicatioinContext context) {
        //有九种策略
        //针对于每个用户请求，都会经过一些处理的策略之后，最终才能有结果输出
        //每个策略可以自定义干预，但是最终的结果都是一致（ModelAndView）

        //=====================这里就是传说中的九大组件===========================
        initMultipartResolve(context);//文件上传解析，如果请求类型是multipart，将通过MultipartResolver进行文件上传解析
        initLocaleResolver(context);//本地话解析
        initThemeResolver(context);//主题解析

        //自己实现
        //HandlerMapping用来保存Controller中配置的RequestMapping和Method的一个对应关系
        initHandlerMappings(context);//通过HandlerMapping将请求映射到处理器

        //自己实现
        //HandlerAdapter用来匹配Method参数，包括转换，动态赋值
        initHandlerAdapters(context);//通过HandlerAdapter进行多种类型的参数动态匹配


        initHandlerExceptionResolver(context);//如果执行过程中遇到异常，将交给HandlerException处理
        initRequestToViewNameTranslator(context);//直接解析请求到视图名

        //自己实现
        //通过ViewResolver实现动态模板的解析
        //自己解析一套模板语言
        initViewResolver(context);//通过viewResolver解析逻辑视图到具体视图实现


        initFlashMapManager(context);//flash映射管理器


    }

    private void initFlashMapManager(ApplicatioinContext context) {
    }

    private void initThemeResolver(ApplicatioinContext context) {
    }

    private void initLocaleResolver(ApplicatioinContext context) {
    }

    private void initMultipartResolve(ApplicatioinContext context) {
    }

    private void initRequestToViewNameTranslator(ApplicatioinContext context) {
    }

    private void initHandlerExceptionResolver(ApplicatioinContext context) {
    }



    //自己实现
    //将Controller中配置的RequestMpaping和Method进行一一对应
    private void initHandlerMappings(ApplicatioinContext context) {
        //按照我们通常的理解应该是一个Map
        //Map<String,Method> map;
        //map.put(url,Method) 通过封装一个新的对象，url模式可以设置成支持正则

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanNam : beanDefinitionNames) {
            Object controller = context.getBean(beanNam);
            Class<?> clazz = controller.getClass();
            //不是所有牛奶都叫特仑苏
            if (!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }

            String baseUrl = "";

            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value().trim();
            }

            //扫描下面所有的公共方法
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }

                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String regex = ("/" + baseUrl + requestMapping.value().trim()).replaceAll("\\*", ".*").replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                handlerMappings.add(new HandlerMapping(controller, method, pattern));
                System.out.println("Mapping:" + regex + "," + method);
            }


        }


    }




    //自己实现
    private void initHandlerAdapters(ApplicatioinContext context) {
        //在初始化阶段，我们能做的是，将这些参数的名字或者类型按一定的顺序保存下来
        //后面通过反射调用的时候，穿的形参是一个数组
        //可以通过记录这些参数的位置index，挨个从数组中填值，这样的话，就和参数的顺序无关了
        for (HandlerMapping handlerMapping : handlerMappings) {
            //每一个方法都有一个参数列表，那么这里保存的是形参列表
            Map<String, Integer> paramMapping = new HashMap<String, Integer>();

            //这里只是命名参数
            Annotation[][] parameterAnnotations = handlerMapping.getMethod().getParameterAnnotations();
            for(int i = 0 ;i < parameterAnnotations.length ; i++){
                for(Annotation a :parameterAnnotations[i]){
                    if(a instanceof  RequestParam){
                        String paramName = ((RequestParam) a).value();
                        if(!"".equals(paramName)){
                            paramMapping.put(paramName,i);
                        }
                    }
                }
            }


            //接下来，我们处理非命名参数
            //只处理Request和Response
            Class<?>[] parameterTypes = handlerMapping.getMethod().getParameterTypes();
            for(int i = 0; i<parameterTypes.length;i++){
                Class<?> type = parameterTypes[i];
                if(type ==HttpServletResponse.class || type == HttpServletRequest.class){
                    paramMapping.put(type.getName(), i);
                }
            }


               this.handlerAdapter.put(handlerMapping,new HandlerAdapter(paramMapping));

        }



    }


    //自己实现
    private void initViewResolver(ApplicatioinContext context) {

        //页面输入一个http://localhost/first.html
        //解决页面名字和模板文件关联的问题
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);

        for (File template : templateRootDir.listFiles()) {
            this.viewResolvers.add(new ViewResolver(template.getName(), template));
        }

    }


}
