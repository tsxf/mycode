package com.mycode.designpattern.obsserve.event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 12:26
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
//单例维护一个读者可以看到坐着的容器列表
public class WriterManger {

    private Map<String, Writer> map = new HashMap<>();

    //私有构造器
    private WriterManger(){

    }

    //单例
    public static WriterManger getInstance(){
     return  WriterMangerHolder.INSTANCE;
    }

    static class WriterMangerHolder{
        private final static WriterManger INSTANCE = new WriterManger();
    }

   //添加一个作者在容器当中
    public void addWriter(Writer writer){
        map.put(writer.getName(), writer);
    }

    //根据作者的名字或者作者对象
    public Writer getWriter(String writerName){
        return map.get(writerName);
    }
}
