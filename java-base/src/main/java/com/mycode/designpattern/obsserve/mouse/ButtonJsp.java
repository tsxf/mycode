package com.mycode.designpattern.obsserve.mouse;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/6 13:46
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 * 假设这个是我们写的某一个特定的jsp页面，里面有很多元素，input，form，table,等等
 * 我们假设只有一个按钮
 */
public class ButtonJsp {
    private Button button;

    public ButtonJsp() {
        super();
        //这个相当于我们在页面上写了一个button元素
        button = new Button();
        button.setId("sumitButton");
        button.setValue("提交");
        button.setClickMouseListener(new ClickMouseListener() {
            //按钮被点，我们就验证后提交
            @Override
            public void onClick(ClickMouseEvent clickMouseEvent) {
                System.out.println("--------单击事件代码---------");
                System.out.println("if('表单合法'){");
                System.out.println("\t表单提交");
                System.out.println("}else{");
                System.out.println("\treturn false");
                System.out.println("}");
            }
        });

        button.setDoubleMouseListener(new DoubleMouseListener() {
            //双击的话我们提示用户不能双击“提交”按钮
            @Override
            public void doubleClick(DoubleMouseEvent doubleMouseEvent) {
                System.out.println("--------双击事件代码---------");
                System.out.println("alert('您不能双击" + doubleMouseEvent.getButton().getValue() + "按钮')");
            }
        });

        button.setMouseMoveListener(new MouseMoveListener() {
            @Override
            public void mouseMove(MouseMoveEvent mouseMoveEvent) {
                //这个我们只简单提示用户鼠标当前位置，示例中加入这个事件
                //目的只是为了说明事件驱动中，可以包含一些特有的信息，比如坐标
                System.out.println("--------鼠标移动代码---------");
                System.out.println("alert('您当前鼠标的位置，x坐标为：" + mouseMoveEvent.getX() + "，y坐标为：" + mouseMoveEvent.getY() + "')");
            }
        });
    }

    public Button getButton() {
        return button;
    }
}
