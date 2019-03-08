package com.mycode.designpattern.obsserve;

import javax.swing.*;
import java.awt.event.*;

/**
 * 蛮小江
 * 2018/8/28 19:46
 */
//异步非阻塞
public class SwingDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Hello Swing");
        frame.setName("Observe use");
        frame.setLocation(300, 300);
        frame.setSize(400, 300);
        frame.setVisible(true);
        //添加监听鼠标点击事件
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                println("鼠标点击，x:" + e.getX() + ",y:" + e.getY());
                super.mouseClicked(e);
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                println("窗口即将关闭");
                frame.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                println("窗口已经关闭");
                System.exit(0);
            }
        });
        println("程序启动成功");

    }

    private static void println(Object vlaue) {
        System.out.println("[线程]" + Thread.currentThread().getName() + "发送" + String.valueOf(vlaue) + "数据");
    }
}
