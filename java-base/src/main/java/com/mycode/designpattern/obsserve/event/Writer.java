package com.mycode.designpattern.obsserve.event;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 12:16
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Writer {
    //作者的名字
    private String name;

    //小说
    private String lastNovel;

    //维护一个读者注册列表
    private Set<WriterListener> set = new HashSet<>();

    public Writer(String name) {
        super();
        this.name = name;
        //注册被观察者writer到维护到作者名字和作家容器中取
        WriterManger.getInstance().addWriter(this);
    }

    public String getName() {
        return name;
    }

    public String getLastNovel() {
        return lastNovel;
    }

    //提供一个供读者注册的方法
    public void registerReader(WriterListener writerListener) {
        set.add(writerListener);
    }


    //取消读者的注册在列表里面
    public void unRegisterReader(WriterListener writerListener) {
        set.remove(writerListener);
    }

    //添加小说
    public void addNovel(String lastNovel) {
        this.lastNovel = lastNovel;
        System.out.println(name + " 发布了新书《" + lastNovel + "》");
        //触发监听事件,通知所有监听这个事件的监听器
        fireEvent();
    }

    private void fireEvent() {
        WriterEvent writerEvent = new WriterEvent(this);
        for (WriterListener writerListener : set) {
            writerListener.addNovel(writerEvent);
        }
    }

}
