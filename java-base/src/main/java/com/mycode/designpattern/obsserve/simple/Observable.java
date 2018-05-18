package com.mycode.designpattern.obsserve.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 9:10
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Observable {
    List<Observer> observerList = new ArrayList<>();
    public void add(Observer observer){
        observerList.add(observer);
    }
    public  void changed(){
        System.out.println("我是被观察者，我已经发生了变化了");
        //通知观察自己的所有观察者
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update(this);
        }
    }


}
