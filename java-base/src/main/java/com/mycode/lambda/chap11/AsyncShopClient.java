package com.mycode.lambda.chap11;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * jf
 * 2018/10/21 19:02
 */
public class AsyncShopClient {
     public static void main(String [] args){
         AsyncShop shop = new AsyncShop("BestShop");
         long start = System.nanoTime();
         Future<Double> futurePrice = shop.getPrice("myPhone");
         long invocationTime = (System.nanoTime()-start)/1_000_000;
         System.out.println("Invocation returned after " + invocationTime + " msecs");

         try {
             System.out.println(futurePrice.get());
         } catch (InterruptedException |  ExecutionException e) {
             e.printStackTrace();
         }
         long retrivalTime = ((System.nanoTime() - start) / 1_000_000);
         System.out.println("Price returned after " + retrivalTime + " msecs");
     }
}
