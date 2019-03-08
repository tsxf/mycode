package com.mycode.lambda.chap09;

import com.mycode.io.resource.Main;

/**
 * jf
 * 2018/10/14 15:02
 */
public class Diamond {
    public static void main(String [] args){
           new D().hello();
    }

    static interface A{
        default void hello(){
            System.out.println("Hello from A");
        }
    }

    static interface B extends A{}
    static interface C extends A{}
    static class D implements B,C{

    }
}
