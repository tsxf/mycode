package com.mycode.lambda.chap07;

import java.io.InputStreamReader;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

/**
 * jf
 * 2018/10/9 16:32
 */
public class ParallelStreamsHarness {

    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public static void main(String[] args) {
        System.out.println("Iterative Sum done in: " + measure(ParallelStreams::interactiveSum, 10_000_000L) + " msecs");
        System.out.println("Sequential Sum done in: " + measure(ParallelStreams::sequentialSum, 10_000_000L) + " msecs");
        System.out.println("Parallel forkJoinSum done in: " + measure(ParallelStreams::parallelSum, 10_000_000L) + " msecs" );
        System.out.println("Range forkJoinSum done in: " + measure(ParallelStreams::rangedSum, 10_000_000L) + " msecs");
        System.out.println("Parallel range forkJoinSum done in: " + measure(ParallelStreams::parallelRangedSum, 10_000_000L) + " msecs" );
        System.out.println("ForkJoin sum done in: " + measure(ForkJoinSumCalculator::forkJoinSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect sum done in: " + measure(ParallelStreams::sideEffectSum, 10_000_000L) + " msecs" );
        System.out.println("SideEffect prallel sum done in: " + measure(ParallelStreams::sideEffectParallelSum, 10_000_000L) + " msecs" );
    }

    public static <T, R> long measure(Function<T, R> f, T input) {
        long faster = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result:" + result);
            if (duration < faster) {
                faster = duration;
            }
        }
        return faster;
    }
}
