package com.mycode.designpattern.obsserve.real;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 10:14
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 单例的容器，保存了一份独有的作者列表，让读者自由的添加订阅的读者或者取消订阅
 */
public class WriterManger {
    //定义观察者容器
    private Map<String,Writer> map = new HashMap<>();

    //私有构造器，不允许外部调用实例化，只有JVM可以
    private  WriterManger(){

    }

    //添加作者
   public  void add(Writer writer){
       map.put(writer.getName(),writer);
   }

   //根据作者的名字获取作者
    public Writer getWriter(String writerName){
        return  map.get(writerName);
    }


    //单例
    public static  final WriterManger getInstance(){
          return  WriterMangerHodler.instance;
    }

   private  static class  WriterMangerHodler {

        private  static  WriterManger instance = new WriterManger();
    }

}
