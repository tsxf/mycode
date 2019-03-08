package com.mycode.lambda.chap07;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * jf
 * 2018/10/10 10:22
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2,jvmArgs = {"-Xms4G","-Xmx4G"})
@Measurement(iterations = 2)
@Warmup(iterations = 3)
public class ParallelStreamBenchmark {
    private static final long N = 10_000_000L;

    @Benchmark
    public long iterativeSum(){
        long result = 0;
        for (long i = 1L; i <N ; i++) {
            return result+=i;
        }
        return result;
    }

    @Benchmark
    public long sequentialSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N).reduce(Long::sum).get();
    }

    @Benchmark
    public long parallelSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N).parallel().reduce(0L,Long::sum);
    }

    @Benchmark
    public long rangedSum() {
        return LongStream.rangeClosed(1, N).reduce(0, Long::sum);
    }

    public long parallelRangedSum() {
        return LongStream.rangeClosed(1, N).parallel().reduce(0, Long::sum);
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }

}
