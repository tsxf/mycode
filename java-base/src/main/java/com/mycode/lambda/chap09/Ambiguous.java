package com.mycode.lambda.chap09;

/**
 * jf
 * 2018/10/14 14:58
 */
//有歧义，声明调用父类方法
public class Ambiguous {
          public static void main(String [] args){
                new C().hello();
          }

          static interface A{
              default void hello(){
                  System.out.println("Hello from A");
              }
          }
          static interface B{
              default void hello(){
                  System.out.println("Hello from B");
              }
          }

          static class C implements B,A{
              @Override
              public void hello() {
                  A.super.hello();
              }
          }
}
