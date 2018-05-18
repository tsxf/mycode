package com.mycode.designpattern.obsserve.event;

import java.util.EventObject;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 12:15
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class WriterEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    //事件持有原始对象信息,有多少个事件源，就有多少个事件和事件监听器
    public WriterEvent(Writer source) {
        super(source);
    }

    public Writer getWriter(){
        return (Writer)super.getSource();
    }
}
