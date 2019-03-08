package com.mycode.lambda.chap13;

import java.util.stream.LongStream;

/**
 * jf
 * 2018/10/22 10:58
 */
public class Recursion {
    public static void main(String [] args){
        System.out.println(factorialIterative(5));
        System.out.println(factorialRecursive(5));
        System.out.println(factorialStreams(5));
        System.out.println(factorialTailRecursive(5));
    }

    public static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1;i <= n ;i++) {
            r *= i;
        }
        return r;
    }

    public static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    public static long factorialStreams(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long a, long b) -> a * b);
    }


    public static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    private static long factorialHelper(long i, long n) {
        return n == 1 ? i : factorialHelper(i * n, n - 1);
    }


}
