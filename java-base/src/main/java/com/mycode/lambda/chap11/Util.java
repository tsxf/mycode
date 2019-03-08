package com.mycode.lambda.chap11;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * jf
 * 2018/10/21 17:24
 */
public class Util {
    private static final Random RANDOM = new Random(0);
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##",
            new DecimalFormatSymbols(Locale.US));

    public static void  delay(){
        int delay = 1000;
      //  int delay = 500 + RANDOM.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }


    public static double formatter(double number) {
        synchronized (FORMAT){
        return new Double(FORMAT.format(number));
        }
    }

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));

        allDoneFuture.thenApply(v -> futures.stream()
                .map(future -> future.join())
                .collect(Collectors.toList()));

        return CompletableFuture.supplyAsync(() -> futures.stream()
                .map(future -> future.join())
                .collect(Collectors.<T>toList()
                )
        );

    }
}
