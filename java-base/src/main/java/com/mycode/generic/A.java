package com.mycode.generic;

import java.lang.reflect.Member;

/**
 * 蛮小江
 * 2018/8/10 9:59
 */
public class A {
    private static  String name;


    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        A.name = name;
    }

    public static void main(String[] args) {
         A a = new A();
         A.setName("hello");
        System.out.println(B.sayName());
    }

}
class B{

    private static String message = A.getName();

    public static String sayName(){
        return message;
    }

}
