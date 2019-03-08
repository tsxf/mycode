package com.mycode.lambda.chap01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 蛮小江
 * 2018/9/18 20:58
 */
public class FilteringApples {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
        //选出绿色的苹果
        List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
        System.out.println("greenApples:"+greenApples);
        //选出重量大于150的苹果
        List<Apple> heavyApples =  filterApples(inventory, FilteringApples::isHeavyApple);
        System.out.println("heavyApples:"+heavyApples);

        //直接在判断条件，谓词（predicate）中 写
        List<Apple> greenApples2 =  filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()));
        System.out.println("greenApples2"+greenApples2);
        List<Apple> heavyApples2 =filterApples(inventory, (Apple apple) ->  apple.getWeight() > 150);
        System.out.println("heavyApples2 :"+heavyApples2 );
        //多个条件组合
        List<Apple>  weirdApples =  filterApples(inventory,(Apple apple)-> "brown".equals(apple.getColor()) || apple.getWeight()  < 80 );
        System.out.println("weirdApples:"+weirdApples);

        //转化为流式处理
        inventory.stream().filter((Apple apple)-> apple.getWeight() > 150).collect(toList());
       //并行处理
        inventory.parallelStream().filter((Apple apple)-> apple.getWeight() > 150).collect(toList());

    }

    //拎出来判断条件，清晰化
    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    //拎出来判断条件，清晰化
    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    //把判断条件传过来，适配要寻找的apple
    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    //传统写法，过滤选出绿色的apple
    public List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    //传统写法，过滤选出重的apple
    public List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }


    //定义一个apple，有颜色，重量属性
    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
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
}
