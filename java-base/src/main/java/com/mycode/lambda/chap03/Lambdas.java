package com.mycode.lambda.chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * jf
 * 2018/10/2 16:57
 */
public class Lambdas {
    public static void main(String[] args) {
        //简单例子
        Runnable r = () -> System.out.println("Hello");
        r.run();

        //用lambda使用Filter
       List<Apple>  inventory = Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

       //选出绿色的Apple
        List<Apple> greenApples = inventory.stream()
                .filter((Apple apple) -> "green".equals(apple.getColor())).collect(Collectors.toList());

        List<Apple> greenApples1=  filter(inventory,(Apple apple)->"green".equals(apple.getColor()));
        System.out.println(greenApples);
        System.out.println(greenApples1);

        //排序，按照重量升序排列

        Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        inventory.sort(c);

        System.out.println(inventory);
    }

    private static List<Apple> filter(List<Apple> inventory, ApplePredicate applePredicate) {
        List<Apple> returnList = new ArrayList<>();
        inventory.forEach((Apple apple)->{
            if(applePredicate.test(apple)){
                returnList.add(apple);
            }
        });
        return returnList;
    }

    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "weight=" + weight +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    interface ApplePredicate {
        boolean test(Apple apple);
    }
}
