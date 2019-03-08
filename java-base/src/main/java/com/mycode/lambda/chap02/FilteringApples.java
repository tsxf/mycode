package com.mycode.lambda.chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 蛮小江
 * 2018/9/19 8:07
 */
public class FilteringApples {
    public static void main(String [] args){
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        List<Apple> greenApples = filterApplesByColor(inventory, "green");
        System.out.println("greenApples:"+greenApples);

        List<Apple> weightApples = filterApplesByWeight(inventory, 150);
        System.out.println("weightApples:"+weightApples);

        List<Apple > greenApples2 = filter(inventory, new AppleColorPredicate());
        System.out.println("greenApples2:"+greenApples2);

        List<Apple> weightApples2 = filter(inventory, new AppleWeightPredicate());
        System.out.println("weightApples2:"+weightApples2);

        List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
        System.out.println("redAndHeavyApples:"+redAndHeavyApples);


        //匿名函数
        //想要找出红色的苹果
 /*       List<Apple> redApples = filter(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println("redApples:"+redApples);*/


        //java8 语法 lambda
        List<Apple> redApples2 =   filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println("redApples2:"+redApples2);

        //其他方面测试
        List<Apple> testApples = filter(inventory, (Apple apple) -> apple.getWeight() % 2 == 0);
        System.out.println("testApples:"+testApples);

        //加入排序
        inventory.sort((Apple a1,Apple a2)->a1.getWeight().compareTo(a2.getWeight()));
        System.out.println("sorted list after:"+inventory);
      /*  inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return 0;
            }
        });*/

    }

    //传统写法，不是通过行为参数化
    public static List<Apple>  filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return  result;
    }

    //变量 引入
    public static List<Apple>  filterApplesByColor(List<Apple> inventory,String color){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return  result;
    }


    //传统写法，不是通过行为参数化
    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return  result;
    }

    //变量引入
    public static List<Apple> filterApplesByWeight(List<Apple> inventory,int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return  result;
    }

    //行为参数化,通过外部条件传入
/*    public static List<Apple> filter(List<Apple> inventory, ApplePredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return  result;
    }*/

    //行为参数化,通过外部条件传入,类型加入泛型，将List抽象化
    public static <T> List<T> filter(List<T> inventory, ApplePredicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : inventory) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return  result;
    }

    public  static class Apple{
        private int weight;
        private String color;

        public Apple(int weight, String color) {
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

    interface  ApplePredicate<T>{
        boolean test(T t);
    }

    static class AppleColorPredicate implements  ApplePredicate<Apple> {
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    static class AppleWeightPredicate implements ApplePredicate<Apple> {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate<Apple> {
        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }



}
