package com.mycode.designpattern.obsserve.mouse;

import java.util.EventListener;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018-05-06 13:19
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 单击鼠标监听的事件
 */

public interface ClickMouseListener extends EventListener {
    void onClick(ClickMouseEvent clickMouseEvent);
}
