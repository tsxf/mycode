package com.mycode.lambda.chap06;

import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.SortedSet;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static com.mycode.lambda.chap06.Dish.menu;
import static java.util.stream.Collectors.*;

/**
 * jf
 * 2018/10/4 18:24
 */
public class Summarizing {
    public static void main(String [] args){
        System.out.println("Nr. of dishes:"+howManyDishes());
        System.out.println("The most caloric dish is :"+findMostCaloricDish());
        System.out.println("The most caloric dish is :"+findMostCaloricDishUsingComparator());
        System.out.println("Total calories in menu:"+calculateTotalCalories());
        System.out.println("Average calories in menu:"+calculateAverageCalories());
        System.out.println("Menu statistics :"+calculateMenuCalories());
        System.out.println("Short menu :"+getShortMenu());
        System.out.println("Short menu comma separated:"+getShortMenuCommaSeparated());

    }

    //计算菜单有多少个
    private static long howManyDishes(){
        return menu.stream().collect(counting());
    }

    //查找Caloric含量最高的菜
    private static Dish findMostCaloricDish(){
        return menu.stream().collect(reducing((Dish d1,Dish d2)->d1.getCalories()> d2.getCalories() ? d1:d2)).get();
    }

    //用比较器来实现->查找Caloric含量最高的菜
    private static Dish findMostCaloricDishUsingComparator(){
        Comparator<Dish> dishCaloricComparator = Comparator.comparingInt(Dish::getCalories);
        BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloricComparator);
        return menu.stream().collect(reducing(moreCaloricOf)).get();
    }

    //Caloric共计多少
    private static int calculateTotalCalories(){
        return menu.stream().collect(summingInt(Dish::getCalories));
    }

    //Caloric计算平均
    private static Double calculateAverageCalories(){
        return menu.stream().collect(averagingDouble(Dish::getCalories));
    }

    //返回含有Caloric，平均，汇总的对象
    private static IntSummaryStatistics calculateMenuCalories(){
        return menu.stream().collect(summarizingInt(Dish::getCalories));
    }

    //找出菜单名字
    private static String getShortMenu(){
        return menu.stream().map(Dish::getName).collect(joining());
    }

    //用逗号分割的菜单
    private static  String getShortMenuCommaSeparated(){
        return menu.stream().map(Dish::getName)
                .collect(joining(","));
    }
}
