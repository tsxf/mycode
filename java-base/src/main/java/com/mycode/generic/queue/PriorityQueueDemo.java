package com.mycode.generic.queue;

import java.time.LocalDate;
import java.util.PriorityQueue;

/**
 * 蛮小江
 * 2018/8/14 8:37
 */
public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<LocalDate> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(LocalDate.of(1906, 12, 22));
        priorityQueue.add(LocalDate.of(2010, 8, 7));
        priorityQueue.add(LocalDate.of(1991, 9, 23));
        priorityQueue.add(LocalDate.of(2018, 8, 14));
        System.out.println("Iterating over elements");
        for (LocalDate localDate :priorityQueue){
            System.out.println(localDate);
        }
        System.out.println("Removing elements");
        while(!priorityQueue.isEmpty()){
                 priorityQueue.remove();
            for (LocalDate localDate :priorityQueue){
                System.out.println(localDate);
            }
            System.out.println("----------------------");
        }

    }
}
