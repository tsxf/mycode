package com.mycode.designpattern.obsserve;

import java.util.Observable;

/**
 * 蛮小江
 * 2018/8/28 19:17
 */
//同步非阻塞
public class ObserveableDemo {
    public static void main(String[] args) {
        MyObserable obserable = new MyObserable();
        obserable.addObserver((o, value) -> {
            System.out.println(Thread.currentThread().getName()+"线程-观察者-1收到更新 " + o.getClass().getSimpleName() + "的数据：" + value);
        });
        obserable.addObserver((o, value) -> {
            System.out.println(Thread.currentThread().getName()+"线程-观察者-2收到更新 " + o.getClass().getSimpleName() + "的数据：" + value);
        });
        obserable.addObserver((o, value) -> {
            System.out.println(Thread.currentThread().getName()+"线程-观察者-3收到更新 " + o.getClass().getSimpleName() + "的数据：" + value);
        });
        //告诉观察者已经改变了
        obserable.setChanged();
        System.out.println(Thread.currentThread().getName()+"发送Hello World数据");
        //通知观察者信息
        obserable.notifyObservers("Hello World");

    }

    private static class MyObserable extends Observable {
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }
    }
}
