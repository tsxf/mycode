package com.mycode.juc.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * jf
 * 2019/2/25 13:36
 */
public class FututerTaskTest {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(0);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            System.out.println(integer.getAndIncrement());
        }, 2, TimeUnit.SECONDS);
        scheduler.shutdown();
    }
}
