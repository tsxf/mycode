package com.mycode.lambda.chap07;

import com.google.common.primitives.Longs;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * jf
 * 2018/10/9 9:49
 */
public class ParallelStreams {
    public static void main(String[] args) {

    }

    public static long interactiveSum(long n){
          long result = 0;
          for (long i = 0; i<=n;i++) {
              result +=i;
          }
          return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L,i->i+1).limit(n).reduce(Long::sum).get();
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).parallel().limit(n).reduce(Long::sum).get();
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1,n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1,n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }


    //通过共享变量的方式，存在资源竞争
    public static class Accumulator {
        private long total = 0;
        public void add(long value){
            total+=value;
        }
    }



    public static void testCpuAvailableProcess(){
        //可用内核的数量
        int count = Runtime.getRuntime().availableProcessors();
        System.out.println(count);
        long maxValue = Long.MAX_VALUE;
        System.out.println(maxValue);
    }
}
