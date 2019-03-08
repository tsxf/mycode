package com.mycode.generic.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 蛮小江
 * 2018/8/10 8:39
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> a = new LinkedList<>();
        a.add("A");
        a.add("C");
        a.add("E");

        System.out.println("a:"+a);
        LinkedList<String> b = new LinkedList<>();
        b.add("B");
        b.add("D");
        b.add("F");
        b.add("G");
        System.out.println("b:"+b);

        //merge  from b to a
       //对于插入者 ListIterator，获取遍历元素 Iterator就可以
        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        while(bIter.hasNext()){
             if(aIter.hasNext()){
                 aIter.next();
             }
            aIter.add(bIter.next());
        }

        System.out.println("a:"+a);


        //每隔一个元素删除
        bIter = b.iterator();
        while (bIter.hasNext()){
            bIter.next();//skip one
            if (bIter.hasNext()) {
                bIter.next(); //skip two
                bIter.remove(); //remove that
            }
        }

        System.out.println("b:"+b);

        //removeAll  b from a
        a.removeAll(b);
        System.out.println("a:"+a);


    }
}
