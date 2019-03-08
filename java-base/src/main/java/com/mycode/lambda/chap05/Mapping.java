package com.mycode.lambda.chap05;

import com.mycode.lambda.chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * jf
 * 2018/10/2 19:33
 */
public class Mapping {
    public static void main(String [] args){
        List<String> dishNames = Dish.menu.stream()
                .map(d -> d.getName())
                .collect(Collectors.toList());
        System.out.println(dishNames);

        System.out.println("-------");
        //map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(wordLengths);

        System.out.println("-------");
        //flatMap
        words.stream()
                .flatMap((String line)->Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::println);
        System.out.println("-------");
        //flatMap
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(6, 7, 8);
        List<int[]> paris = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream()
                                    .map((Integer j) -> new int[]{i, j})
                )
                .filter(pair -> ((pair[0] + pair[1] )% 3 == 0))
                .collect(Collectors.toList());

        paris.forEach(pair-> System.out.println("("+pair[0]+","+pair[1]+")"));

    }
}
