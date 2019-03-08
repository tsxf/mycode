package com.mycode.juc.thread;

/**
 * 蛮小江
 * 2018/8/10 22:47
 */
public class AtomicDemo {
    private static volatile   int count = 0;
    public  static  void inc(){
      /*  try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        count++;
    }
    public static void main(String[] args) throws InterruptedException {
            for (int i = 0 ;i < 1000;i++){
               new Thread(AtomicDemo::inc).start();
               // new Thread(()->AtomicDemo.inc()).start();
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AtomicDemo.inc();
                    }
                }).start();*/
            }

        //  Thread.sleep(4000);
        System.out.println(count);
    }
}
