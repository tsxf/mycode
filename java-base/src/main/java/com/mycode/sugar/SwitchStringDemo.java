package com.mycode.sugar;

/**
 * 蛮小江
 * 2018/8/9 8:31
 */
public class SwitchStringDemo {
    public static void main(String[] args) {
        String str = "worold";
        switch (str) {
            case "hello" :
                System.out.println("hello");
            break;
            case "world" :
                System.out.println("world");
                break;
                default:
                    break;
        }
    }
}
