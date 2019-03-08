package com.mycode.lambda.chap07;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;
import static com.mycode.lambda.chap07.ParallelStreamsHarness.FORK_JOIN_POOL;

/**
 * jf
 * 2018/10/9 16:54
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
  public static final  long THRESHOLD = 10_000;
  private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers,0,numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length < THRESHOLD) {
            //处理最小单元
          return   computeSequentially();
        }

        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        //异步启动
        leftTask.fork();

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        //同步计算执行
        Long rightResult = rightTask.compute();

        //等待leftTask任务完成
        Long leftResult = leftTask.join();

        return leftResult+rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start ; i < end ; i++) {
            sum +=i;
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }
}
