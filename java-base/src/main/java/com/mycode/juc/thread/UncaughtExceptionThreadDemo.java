package com.mycode.juc.thread;

/**
 * 蛮小江
 * 2018/8/16 14:01
 */
public class UncaughtExceptionThreadDemo  implements Runnable, Thread.UncaughtExceptionHandler{
    public static void main(String[] args) throws InterruptedException {
        UncaughtExceptionThreadDemo t = new UncaughtExceptionThreadDemo();
        Thread thread =   new Thread(t,"thread-uncaught-demo-1");
        thread.setUncaughtExceptionHandler(t);
        thread.start();
        Thread.sleep(2000);
        System.out.println("Main method thread state="+thread.getState());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(" thread name="+t.getName()+",thread state="+t.getState()+",cause="+e.getCause()+",message="+e.getMessage());
    }

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            i++;
            if(i % 1000==0){
                System.out.println("current i value="+i);
                throw new NullPointerException("故意抛出一个空指针异常");
            }
        }
        System.err.println("线程终止运行");
    }
}
