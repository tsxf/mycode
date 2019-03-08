package com.mycode.juc.thread;

/**
 * 蛮小江
 * 2018/8/10 23:09
 */
public class InterruptedDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(()->{
            int i = 0;
               while(!Thread.currentThread().isInterrupted()){

                     if(i > 1000){

                         System.out.println(""+Thread.currentThread().getName()+"-1:"+ Thread.currentThread().isInterrupted());
                         Thread.currentThread().interrupt();
                         System.out.println(""+Thread.currentThread().getName()+"-2:"+ Thread.currentThread().isInterrupted());
                         System.out.println(""+Thread.currentThread().getName()+"-3:"+ Thread.currentThread().isInterrupted());
                         System.out.println(""+Thread.currentThread().getName()+"-4:"+ Thread.currentThread().isInterrupted());
                         try {
                             Thread.sleep(100);
                             System.out.println("=====>会执行？");
                         } catch (InterruptedException e) {
                             //会重置状态
                             System.err.println("被中断"+Thread.currentThread().getName()+"-5:"+ Thread.currentThread().isInterrupted());
                             try {
                                 Thread.sleep(1000);
                                 System.out.println("-------->中断后会休眠？");
                             } catch (InterruptedException e1) {
                                 e1.printStackTrace();
                                 System.err.println("被中断后再次休眠："+Thread.currentThread().getName()+"-6:"+ Thread.currentThread().isInterrupted());

                             }
                             System.out.println("-------->中断后会休眠？结果："+Thread.currentThread().isInterrupted());
                             e.printStackTrace();
                             break;
                         }
                         System.out.println("=======>休眠成功");
                     }

                     i++;
               }
            System.err.println(Thread.currentThread().getName()+"线程被中断");
        },"thread-Test-");


        thread.start();
        System.out.println( "thread.isAlive():"   +thread.isAlive());
       Thread.sleep(4000);
        //如果被中断过，返回false?
        System.out.println("before:"+thread.isInterrupted());

       //终止线程，下面1,2，3重复调用都是true
        System.out.println("after-1:"+thread.isInterrupted());

        thread.interrupt();
        thread.interrupt();
        thread.interrupt();
        System.out.println("after-2:"+thread.isInterrupted());
        System.out.println("after-3:"+thread.isInterrupted());

        //清楚终止标记
        Thread.interrupted();
        Thread.sleep(1000);
        System.out.println("after-4:"+thread.isInterrupted());

        //清楚标记后，再调用 thread.interrupt()一致都是false
        thread.interrupt();
        System.out.println("after-5:"+thread.isInterrupted());

        thread.interrupt();
        System.out.println("after-6:"+thread.isInterrupted());

        thread.interrupt();
        System.out.println("after-7:"+thread.isInterrupted());

    }
}
