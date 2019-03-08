package com.mycode.lambda.chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * jf
 * 2018/10/2 17:16
 */
public class Sorting {
    public static void main(String [] args){
      //创建数据源

        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red")));

        //排序
        //自己实现排序规则
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        //重新改变集合顺序
        inventory.set(1, new Apple(30, "green"));

        //使用匿名内部类
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        System.out.println(inventory);


        //使用lambda
        inventory.set(1, new Apple(20, "red"));
        inventory.sort((a1,a2)->a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);

        //使用方法引用
        inventory.set(1, new Apple(10, "red"));
        //comparing 键值器返回Comparator比较,根据weight来做比较
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);



    }

    public static class Apple{
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }

    static class AppleComparator implements Comparator<Apple>{

        @Override
        public int compare(Apple o1, Apple o2) {
            return o1.getWeight().compareTo(o2.getWeight());
        }
    }
}
