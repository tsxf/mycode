package com.mycode.lambda.chap05;

import com.mycode.lambda.chap02.MeaningOfThis;
import com.mycode.lambda.chap04.Dish;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.mycode.lambda.chap04.Dish.menu;

/**
 * jf
 * 2018/10/2 20:30
 */
public class Reducing {
    public static void main(String [] args){
        List<Integer> numbers = Arrays.asList(3, 4, 5, 6, 1, 2);
        Integer sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println(sum);

        Integer sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        Integer max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> max1 = numbers.stream().reduce(Integer::max);
        max1.ifPresent(System.out::println);

        Integer calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        System.out.println("Number of calories:"+calories);


    }
}
