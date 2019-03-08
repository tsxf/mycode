package com.mycode.lambda.chap06;

import java.util.*;
import java.util.stream.Collectors;

import static com.mycode.lambda.chap06.Dish.dishTags;
import static com.mycode.lambda.chap06.Dish.menu;
import static java.util.stream.Collectors.*;


/**
 * jf
 * 2018/10/4 19:17
 */
public class Grouping {
    enum CaloricLevel {
        DIET, NORMAL, FAT
    }

    public static void main(String[] args) {
        System.out.println("Dish grouped by type;" + groupDishesByType());
        System.out.println("Dish names grouped by type:" + groupDishNamesByType());
        System.out.println("Dish tags grouped by type:" + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by caloric level:" + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOptionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    private static Map<Dish.Type,Integer> sumCaloriesByType() {
       return  menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
       return menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
    }



    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        //menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
     return menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    }

    //自定义分组条件，只要满足Function接口定义就可以
    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy((Dish dish) -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })
        );
    }

    //分两次，分别是Type,CaloricLevel
    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return menu.stream().collect(
                groupingBy(Dish::getType, groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (dish.getCalories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else {
                                return CaloricLevel.FAT;
                            }
                        })
                ));
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }

    //按照Type分组，寻找每个 分组中最高 卡路里的数值
    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2))
        );
    }

    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
      //  List<Dish> menu = new ArrayList<>();
        return menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(
                                reducing(
                                        (Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2), Optional::get)
                ));

    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType, mapping((Dish dish) -> {
                            if (dish.getCalories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (dish.getCalories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else {
                                return CaloricLevel.FAT;
                            }
                        }, toSet())
                ));
    }


}
