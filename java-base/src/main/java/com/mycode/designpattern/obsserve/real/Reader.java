package com.mycode.designpattern.obsserve.real;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 10:08
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 读者类要实现观察者接口，观察者更新内容会通知,实现其update方法
 */
public class Reader implements Observer {

    //读者的名字
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    //读者自己可以关注某一位作者，关注则代表把自己加入到作者的观察者列表里面
    //每一个Observerable（Writer）都有一份自己的观察者列表
    public void subscribe(String writerName) {
        //利用Writer的父类Observe里面的容器,保存读者列表
        WriterManger.getInstance().getWriter(writerName).addObserver(this);
    }

    //读者可以取消关注某一位作者，取消关注则代表把自己从作者的观察者列表里面删除
    public void unSubscribe(String writerName) {
        //利用Writer的父类Observe里面的容器删除先前添加的Reader
        WriterManger.getInstance().getWriter(writerName).deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Writer) {
            Writer writer = (Writer) o;
            System.out.println(name + " 知道" + writer.getName() + "写了新书《" + writer.getLastNovel()+"》");
        }
    }
}
