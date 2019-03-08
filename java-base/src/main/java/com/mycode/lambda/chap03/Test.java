package com.mycode.lambda.chap03;

/**
 * jf
 * 2018/9/30 11:23
 */
public class Test {
    public static void main(String [] args){
             String str = "Hello";
        String[] split = str.split("");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }

    }
}
