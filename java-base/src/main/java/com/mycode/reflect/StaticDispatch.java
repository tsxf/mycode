package com.mycode.reflect;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: ${description}
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class StaticDispatch {
     abstract class Humnan {}
     class Man extends Humnan {}
     class Woman extends Humnan {}
    public void hello(Humnan guy) {
        System.out.println("hello, Humnan");
    }

    public void hello(Man guy) {
        System.out.println("hello, Man");
    }

    public void hello(Woman guy) {
        System.out.println("hello, Woman");
    }

    public static  void main(String[] args) {
        Man man = new StaticDispatch().new Man();
        Woman woman = new StaticDispatch().new Woman();
        StaticDispatch dispatch = new StaticDispatch();
        dispatch.hello(man);
        dispatch.hello(woman);
    }


}
