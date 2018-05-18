package com.mycode.designpattern.obsserve.simple;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 9:19
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Client {
    public static void main(String[] args) {
        Observable observable = new Observable();
        Observer observer1 = new ConcreteObserver1();
        Observer observer2 = new ConcreteObserver2();
        observable.observerList.add(observer1);
        observable.observerList.add(observer2);
        observable.changed();
    }
}
