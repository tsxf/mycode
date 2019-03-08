package com.mycode.lambda.chap04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * jf
 * 2018/10/2 17:40
 */
public class StreamBasic {
    public static void main(String [] args){
             //java7
           getLowCaloricDishesNameInJava7(Dish.menu).forEach(System.out::println);

        System.out.println("-----");

        getLowCarloricDishesNameInJava8(Dish.menu).forEach(System.out::println);
    }

    public static List<String> getLowCaloricDishesNameInJava7(List<Dish> dishes){
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }

        List<String> lowCaloricDishesName = new ArrayList<>();
        //排序
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        //加入结果集 中
        for (Dish d : lowCaloricDishes) {
            lowCaloricDishesName.add(d.getName());
        }

        return lowCaloricDishesName;
    }

    public static List<String> getLowCarloricDishesNameInJava8(List<Dish> dishes){

        return  dishes.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
    }
}
