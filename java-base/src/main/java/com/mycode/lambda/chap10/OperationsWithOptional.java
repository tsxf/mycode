package com.mycode.lambda.chap10;

import java.util.Optional;

import static java.util.Optional.*;

/**
 * jf
 * 2018/10/14 18:00
 */
public class OperationsWithOptional {
    public static void main(String [] args){
        System.out.println(max(of(3), of(5)));
        System.out.println(max(empty(), of(5)));

        Optional<Integer> opt1 = of(5);
        Optional<Integer> opt2 = opt1.or(()->of(4));

        System.out.println(empty().or(()->of(4)));


    }

    public static final Optional<Integer> max(Optional<Integer> i, Optional<Integer> j) {
        return i.flatMap(a -> j.map(b->Math.max(a,b)));
    }
}
