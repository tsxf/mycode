package com.mycode.framework.context;

/**
 * 蛮小江
 * 2018/5/9 23:56
 */
//顶层接口，回调设置context上下文
public interface ApplicationContextAware {
    void setApplicationContext(ApplicatioinContext applicationContext);
}
