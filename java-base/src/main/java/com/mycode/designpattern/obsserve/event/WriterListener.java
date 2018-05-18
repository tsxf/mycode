package com.mycode.designpattern.obsserve.event;

import java.util.EventListener;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018 年05月06日 12:18
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
//事件监听器是对事件进行监听，有多少个事件就会有多少个事件监听器
public interface WriterListener extends EventListener {
    void addNovel(WriterEvent writerEvent);
}
