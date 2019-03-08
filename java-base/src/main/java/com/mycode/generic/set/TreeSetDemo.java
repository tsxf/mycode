package com.mycode.generic.set;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 蛮小江
 * 2018/8/12 15:55
 */
public class TreeSetDemo {
    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet<>();
        parts.add(new Item("haha", 12));
        parts.add(new Item("mimimimii", 100));
        parts.add(new Item("hihi", 9));
        parts.add(new Item("ohhoho", 45));
        NavigableSet<Item> sortbyDescription = new TreeSet<>(Item::compareTo);
        sortbyDescription.addAll(parts);
        System.out.println(parts);
    }
}
