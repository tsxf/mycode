package com.mycode.generic.list;

import java.util.*;

/**
 * 蛮小江
 * 2018/8/9 8:15
 */
public class ArrayListDemo {
    public static void main(String[] args) {

        //testBasicUse();
        String [] languages = {"Java","Python","Ruby","Go"};
        List<String> strings = Arrays.asList(languages);
        //使用remove会报异常UnsupportedOperationException，不能使用add,remove
        // strings.remove("Java");
        strings.set(0, "C++");
        System.out.println(languages.length);
        System.out.println(strings);
        //使用的是同一个数据
        System.out.println(Arrays.asList(languages));

        List<String> aDefault = Collections.nCopies(100, "default");
        System.out.println(aDefault);
        aDefault.toArray(new String[0]);

        Set<String> aDefault1 = Collections.singleton("DEFAULT");
        System.out.println(aDefault1);


        List<String> sun = Collections.singletonList("SUN");
        System.out.println(sun);
       /* Set<String> stringSet = Collections.emptySet();
        stringSet.add("hello");
        stringSet.addAll(strings);
        System.out.println(stringSet);*/
        Collections.unmodifiableList(sun);
    }

    private static void testBasicUse() {
        ArrayList<String > list = new ArrayList<>();
        list.add("Hello");
        list.add("world");

        System.out.println(list);

        System.out.println(null==null);
    }

    public static <A extends Comparable<A>> A max(Collection<A> xs) {
        Iterator<A> xi = xs.iterator();
        A w = xi.next();
        while (xi.hasNext()) {
            A x = xi.next();
            if (w.compareTo(x) < 0) {
                w = x;
            }
        }
        return w;
    }
}
