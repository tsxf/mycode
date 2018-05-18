package com.mycode.designpattern.obsserve.simple;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: 这个接口是为了提供一个统一的观察者做出相应行为的方法
 * @Date 2018 年05月06日 9:07
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public interface Observer {
    /**
     * class_name: Observer
     * describe: 通知观察者的行为
     * params:
     * return:
     * creat_user: 蛮小江
     * date_time: 2018/5/6 9:11
     **/

    void update(Observable o);
}
