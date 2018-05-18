package com.mycode.designpattern.obsserve.mouse;

import java.util.EventListener;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018-05-06日 13:22
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 鼠标移动监听事件
 */
public interface MouseMoveListener extends EventListener {
    void mouseMove(MouseMoveEvent mouseMoveEvent);
}
