package com.mycode.generic.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 蛮小江
 * 2018/8/12 15:12
 */
public class SetDemo {
    public static void main(String[] args) {
        Set<String> words = new HashSet<String>();
        long totalTime = 0;
        try(Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()){
                String word = scanner.next();
                long callTime = System.currentTimeMillis();
                words.add(word);
                callTime = System.currentTimeMillis()-callTime;
                totalTime += callTime;
            }
        }

        Iterator<String> iter = words.iterator();
        for(int i= 0; i<20&& iter.hasNext();i++){
            System.out.println( iter.next());
        }
        System.out.println(".........");
        System.out.println(words.size()+" distinct words. "+totalTime+" milliseconds");
    }
}
