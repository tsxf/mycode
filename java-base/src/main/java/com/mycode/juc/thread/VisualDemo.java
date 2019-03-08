package com.mycode.juc.thread;

/**
 * 蛮小江
 * 2018/8/10 23:03
 */
public class VisualDemo {
  //  private static boolean flag = true; //不可见
    private static volatile  boolean flag = true; //不可见

    public static void main(String[] args) throws InterruptedException {
     Thread thread =  new Thread(()->{
            int i = 0;
            //主线程修改，需要保证子线程可见性
            while (flag){
               i++;
            }
         System.out.println(i);
        });
        thread.start();
        Thread.sleep(1);
        flag = false;
    }

}
