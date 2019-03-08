package com.mycode.lambda.chap11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * jf
 * 2018/10/17 15:24
 */
public class Tsest {
    private static Random rand = new Random();
    private static long t = System.currentTimeMillis();
    static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(100);
    }
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(Tsest::getMoreData);
        Future<Integer> f = future.handle((v, e) -> {
            int a = v.intValue()*3 ;
            System.out.println("v:"+a);
            System.out.println(e);
            return v.intValue();
        });
        System.out.println("result:"+f.get());
        System.in.read();
    }
}
