package com.mycode.designpattern.obsserve.mouse;

import java.util.EventListener;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018-05-06 13:21
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 鼠标双击监听事件
 */
public interface DoubleMouseListener extends EventListener {
    void doubleClick(DoubleMouseEvent doubleMouseEvent);
}
