package com.mycode.designpattern.chain.reponsibility;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 蛮小江
 * 2018/8/11 10:57
 */
public class SaveProcessor extends Thread implements  IRequestProcessor {



    private LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();


    @Override
    public void run() {
       while (true){
           try {
               Request request = linkedBlockingQueue.take();
               //处理自己的业务
               System.out.println("save:"+request.getName());

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }


    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }
}
