package com.mycode.lambda.chap11;

import java.util.List;
import java.util.function.Supplier;

/**
 * jf
 * 2018/10/21 19:25
 */
public class BestPriceFinderMain {
    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();
    public static void main(String [] args){
        execute("sequential", () -> bestPriceFinder.findPriceSequential("myPhone27S"));
        execute("parallel", () -> bestPriceFinder.findPriceParallel("myPhone27S"));
        execute("composed CompletableFuture", () -> bestPriceFinder.findPricesFutures("myPhone27S"));
        bestPriceFinder.printPriceStream("myPhone27S");
    }

    private static void execute(String msg, Supplier<List<String>> supplier) {
        long start = System.nanoTime();
        System.out.println(supplier.get());

        long duration = (System.nanoTime()-start)/1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }
}
