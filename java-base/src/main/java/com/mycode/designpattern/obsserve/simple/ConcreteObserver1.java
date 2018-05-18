package com.mycode.designpattern.obsserve.simple;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 9:11
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class ConcreteObserver1 implements  Observer {
    @Override
    public void update(Observable o) {
        System.out.println("观察者1，观察到："+o.getClass().getSimpleName()+"发生变化");
        System.out.println("观察者1做出相应的处理");
    }
}
