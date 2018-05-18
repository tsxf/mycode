package com.mycode.designpattern.obsserve.mouse;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/6 13:25
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 模拟一个html页面的button元素，事件源
 */
public class Button {
    //id属性
    private String id;
    //名字
    private String value;
    //鼠标单击事件监听
    private ClickMouseListener clickMouseListener;
    //鼠标双击事件监听
    private DoubleMouseListener doubleMouseListener;
    //鼠标移动事件监听
    private MouseMoveListener mouseMoveListener;

    //按钮的单击事件
    public  void onClick(){
        clickMouseListener.onClick(new ClickMouseEvent(this) );
    }

    //鼠标的双击行为
    public void doubleClick(){
        doubleMouseListener.doubleClick(new DoubleMouseEvent(this));
    }

    //鼠标的移动事件
    public  void mouseMove(int x,int y){
        mouseMoveListener.mouseMove(new MouseMoveEvent(this,x,y));
    }

    //相当于给id赋值
    public void setId(String id) {
        this.id = id;
    }

    //名字赋值
    public void setValue(String value) {
        this.value = value;
    }

    //给onClick添加函数，设置onClick属性
    public void setClickMouseListener(ClickMouseListener clickMouseListener) {
        this.clickMouseListener = clickMouseListener;
    }

    //同理
    public void setDoubleMouseListener(DoubleMouseListener doubleMouseListener) {
        this.doubleMouseListener = doubleMouseListener;
    }

    //同理
    public void setMouseMoveListener(MouseMoveListener mouseMoveListener) {
        this.mouseMoveListener = mouseMoveListener;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public ClickMouseListener getClickMouseListener() {
        return clickMouseListener;
    }

    public DoubleMouseListener getDoubleMouseListener() {
        return doubleMouseListener;
    }

    public MouseMoveListener getMouseMoveListener() {
        return mouseMoveListener;
    }
}
