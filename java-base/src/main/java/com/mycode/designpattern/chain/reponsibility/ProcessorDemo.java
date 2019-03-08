package com.mycode.designpattern.chain.reponsibility;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 蛮小江
 * 2018/8/11 11:00
 */
public class ProcessorDemo {
    static  ExecutorService executorService;
    private static void initResoure(){
        executorService  = Executors.newCachedThreadPool();
    }
    public static void main(String[] args) throws InterruptedException {
        initResoure();
        SaveProcessor saveProcessor = new SaveProcessor();
        saveProcessor.setName("save-thread-1");
       // saveProcessor.start();
        PrintProcessor printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.setName("print-thread-1");
       // printProcessor.start();

        System.out.println("printProcessor:"+printProcessor);
        System.out.println("saveProcessor:"+saveProcessor);
       executorService.submit(printProcessor);
        executorService.submit(saveProcessor);


        for (int i = 1 ; i <=20 ;i++){
            Request request = new Request();
            request.setName("Hello World "+i);
            printProcessor.processorRequest(request);
          //  Thread.sleep(1000);
        }



        Thread.sleep(4000);


        executorService.shutdown();


        if( !executorService.isShutdown()){
            for (int i = 20 ; i <=40 ;i++){
                Request request = new Request();
                request.setName("Hello World "+i);
                printProcessor.processorRequest(request);
                //  Thread.sleep(1000);
            }

        }

        List<Runnable> runnables = executorService.shutdownNow();
        //空
        for (Runnable runnable :runnables){
            System.out.println("========>"+runnable.getClass().getSimpleName());
        }

        saveProcessor.interrupt();
        printProcessor.interrupt();
        System.out.println("Thread.activeCount()-before:"+Thread.activeCount());
        //只能放到这一步，能够阻止线程池继续接受任务
        Thread.currentThread().interrupt();
        System.out.println("Thread.activeCount()-after:"+Thread.activeCount());
        executorService.awaitTermination(3, TimeUnit.SECONDS);
        //放到这不能终止任务
       // Thread.currentThread().interrupt();


      /*try {
          executorService.awaitTermination(3, TimeUnit.SECONDS);
      }catch (Exception e){
          Thread.currentThread().interrupt();
          e.printStackTrace();
          System.out.println("Thread.activeCount():"+Thread.activeCount());
      }*/


        System.out.println("executorService.isTerminated():"+executorService.isTerminated());;
      //  Thread.sleep(6000);
        for (int i = 40 ; i <=60 ;i++){
            Request request = new Request();
            request.setName("Hello World "+i);
            printProcessor.processorRequest(request);
            //  Thread.sleep(1000);
        }

      /*  Request requestJava = new Request();
        requestJava.setName("Hello java");
        printProcessor.processorRequest(requestJava);

        //Thread.sleep(1000);

        Request requestPython= new Request();
        requestPython.setName("Hello python");
        printProcessor.processorRequest(requestPython);

        //Thread.sleep(1000);

        Request requestRuby= new Request();
        requestRuby.setName("Hello ruby");
        printProcessor.processorRequest(requestRuby);

        //Thread.sleep(1000);

        Request requestGo= new Request();
        requestGo.setName("Hello go");
        printProcessor.processorRequest(requestGo);*/
    }
}
