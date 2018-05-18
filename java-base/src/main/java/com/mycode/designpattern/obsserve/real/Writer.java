package com.mycode.designpattern.obsserve.real;

import java.util.Observable;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 10:14
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Writer extends Observable{
    //作者的名字
    private String name;
    private String lastNovel;//记录作者最新发布的小说

    public Writer(String name) {
        //利用父类Observe通知观察者
        super();
        this.name = name;
        //实例化一个新的作者时，就把作者信息加入到读者可以订阅的作者列表中
        WriterManger.getInstance().add(this);
    }

    public void addNovel(String novle){
        System.out.println(name+" 发布了新书《"+novle+"》！");
        this.lastNovel = novle;
        //设置被观察者已经发生变化
        setChanged();
        //通知观察者
        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public String getLastNovel() {
        return lastNovel;
    }
}
