package com.mycode.designpattern.obsserve.mouse;


import java.util.EventObject;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 13:11
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 按钮事件基类
 */
public abstract class ButtonEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ButtonEvent(Object source) {
        super(source);
    }

    public Button getButton() {
        return (Button) super.getSource();
    }


}


//点击事件
class ClickMouseEvent extends ButtonEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ClickMouseEvent(Object source) {
        super(source);
    }
}

//双击事件
class DoubleMouseEvent extends ButtonEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DoubleMouseEvent(Object source) {
        super(source);
    }
}

//鼠标移动事件
class MouseMoveEvent extends ButtonEvent {
    //鼠标移动事件比较特殊，因为他需要告诉监视器当前鼠标的坐标是什么
    private int x;
    private int y;

    public MouseMoveEvent(Object source, int x, int y) {
        super(source);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
