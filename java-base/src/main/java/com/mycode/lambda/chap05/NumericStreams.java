package com.mycode.lambda.chap05;

import com.mycode.lambda.chap04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.mycode.lambda.chap04.Dish.menu;
import static java.lang.System.out;

/**
 * jf
 * 2018/10/2 20:07
 */
public class NumericStreams {
    public static void main(String [] args){
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        Arrays.stream(numbers.toArray())
                .forEach(out::println);

        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("Number of calories:"+calories);

        //max and OptionalInt

        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max ;
        if (maxCalories.isPresent()) {
            max = maxCalories.getAsInt();
        } else {
            //设置默认值
            max = 1;
        }
        System.out.println(max);
        
        //数值范围
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .boxed()
                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );


              pythagoreanTriples.forEach(t-> System.out.println(t[0]+","+t[1]+","+t[2]));
                
    }
}
