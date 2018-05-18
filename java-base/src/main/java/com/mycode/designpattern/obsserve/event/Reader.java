package com.mycode.designpattern.obsserve.event;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 12:21
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Reader implements  WriterListener {

    //读者的名字
    private String name;

    public Reader(String name) {
        super();
        this.name = name;
    }

    //订阅作者
    public void suscribe(String writerName){
        WriterManger.getInstance().getWriter(writerName).registerReader(this);
    }

    //取消作者
    public  void unSubscribe(String writerName){
        WriterManger.getInstance().getWriter(writerName).unRegisterReader(this);
    }


    @Override
    public void addNovel(WriterEvent writerEvent) {
        Writer writer = writerEvent.getWriter();
        System.out.println(name+"知道" + writer.getName() + "发布了新书《" + writer.getLastNovel() + "》，非要去看！");
    }
}
