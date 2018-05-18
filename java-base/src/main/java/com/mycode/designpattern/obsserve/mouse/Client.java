package com.mycode.designpattern.obsserve.mouse;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/6 13:54
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class Client {
    public static void main(String[] args) {
        //客户访问了我们的这个JSP页面
        ButtonJsp jsp = new ButtonJsp();
        //双击按钮
        jsp.getButton().doubleClick();
        //鼠标移动
        jsp.getButton().mouseMove(100,200);
        jsp.getButton().mouseMove(200,400);
        jsp.getButton().mouseMove(400,700);
        //提交
        jsp.getButton().onClick();
    }
}
