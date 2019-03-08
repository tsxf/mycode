package com.mycode.lambda.chap09;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * jf
 * 2018/10/14 15:15
 */
public class Intro {
    public static void main(String [] args){
        List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
         numbers.sort(Comparator.naturalOrder());
        System.out.println(numbers);
    }
}
