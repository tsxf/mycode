package com.mycode.designpattern.obsserve.real;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 10:52
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Client {
    public static void main(String[] args) {
        //先创建作者，方便读者可以订阅
        Writer writer1 = new Writer("Neo");
        Writer writer2 = new Writer("Bob");
        //创建读者，观察者
        Reader reader1 = new Reader("小王");
        Reader reader2 = new Reader("小李");
        Reader reader3 = new Reader("小张");
        Reader reader4 = new Reader("小梅");
        Reader reader5 = new Reader("小陈");
        //读者订阅作者
        reader1.subscribe("Neo");
        reader2.subscribe("Neo");
        reader3.subscribe("Neo");
        reader4.subscribe("Neo");
        reader5.subscribe("Neo");
        reader2.subscribe("Bob");
        //作者发布新小说
        writer1.addNovel("黑客帝国");
        writer2.addNovel("时间简史");

        //读者小王取消对Neo的订阅
        reader1.unSubscribe("Neo");
        writer1.addNovel("哈利波特");
    }
}
