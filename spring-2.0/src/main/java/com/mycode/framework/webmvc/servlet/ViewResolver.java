package com.mycode.framework.webmvc.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视图解析器，匹配过来的ModelAndView，解析页面，响应成字符串
 * Created by 江富 on 2018/4/30
 */
public class ViewResolver {
    //设置这个类的主要目的是，
    //1. 将一个静态文件变为动态文件
    //2. 根据用户传送参数不同，产生不同的结果
    //3. 最终输出字符串，交给Response处理
    private  String viewName;
    private File templateFile;

    public ViewResolver(String viewName, File templateFile) {
        this.viewName = viewName;
        this.templateFile = templateFile;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public  String viewResolver(ModelAndView modelAndView) throws  Exception{
        StringBuffer sb = new StringBuffer();
        RandomAccessFile ra = new RandomAccessFile(this.templateFile,"r");

        try{
          String line = null;
          while (null != (line=ra.readLine())){
              line = new String (line.getBytes("ISO-8859-1"),"UTF-8");
              Matcher matcher = matcher(line);
              while (matcher.find()){
                  for (int i = 1; i<=matcher.groupCount();i++) {
                       //要把￥{}中间的这个字符串给取出来
                      String paramName = matcher.group(i);
                      Object paramValue = modelAndView.getModel().get(paramName);
                      if(null == paramValue){
                          continue;
                      }
                      line = line.replaceAll("￥\\{"+paramName+"\\}", paramValue.toString());
                      line = new String(line.getBytes("UTF-8"), "ISO-8859-1");
                  }

              }
              sb.append(line);
          }

        }catch (Exception e){
                   e.printStackTrace();
        }finally {
            ra.close();
        }
        return sb.toString();
    }

    private Matcher matcher(String line) {
        Pattern pattern = Pattern.compile("￥\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);


        Matcher matcher = pattern.matcher(line);
        return  matcher;
    }
}
