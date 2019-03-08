package com.mycode.designpattern.chain.reponsibility;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 蛮小江
 * 2018/8/11 10:47
 */
public class PrintProcessor extends  Thread implements IRequestProcessor {
    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();
    private    IRequestProcessor processor;
    public PrintProcessor(IRequestProcessor processor) {
       this.processor = processor;
    }

    @Override
    public void run() {
         while (true){
             try {
                 Request request = linkedBlockingQueue.take();
                 //PrintProcessor先处理自己的业务
                 boolean flag = false;
                 flag = doBiz(request);
                 //Thread.sleep(100);
                 //调用下一个处理器，处理业务
                 if(flag){
                     processor.processorRequest(request);
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
    }

    private  boolean   doBiz(Request request) throws InterruptedException {
      //  Thread.sleep(2000);
        System.err.println("print:"+request.getName());
        return true;
    }

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }
}
