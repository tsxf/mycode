package com.mycode.framework.context;

/**
 * 蛮小江
 * 2018/5/9 23:57
 */
//定义模板方法
public abstract class AbstractApplicationContext {
    //提供给子类重写
   protected void  onRefresh(){

   };

   protected abstract void refreshBeanFactory();

}
