package com.mycode.generic.map;

import java.util.*;

/**
 * 蛮小江
 * 2018/8/14 20:34
 */
enum Language{
    Java,Python,Go,Ruby
};
public class MapTest {

    public static void main(String[] args) {
        //testBsicUse();
        //testPrintMap();
        //testWeakHashMapuse();

        EnumSet<Language> enumSet = EnumSet.allOf(Language.class);
        System.out.println(enumSet);
        EnumSet<Language> noneOf = EnumSet.noneOf(Language.class);
        System.out.println(enumSet );
        EnumSet<Language> range = EnumSet.range(Language.Java, Language.Go);
        System.out.println(range);
        EnumSet<Language> mwf = EnumSet.of(Language.Ruby, Language.Java, Language.Python);
        System.out.println(mwf);

    }

    private static void testWeakHashMapuse() {
        //WeakHashMap  弱引用 如果长时间不活动，没有其他地方使用，GC将回收这个对象
        WeakHashMap<String, Integer> weakHashMap = new WeakHashMap<>();
        weakHashMap.put("java", 1);
        weakHashMap.put("python",2);
        weakHashMap.put("go", 3);
        weakHashMap.put("ruby", 4);
        weakHashMap.keySet().remove("java");
        System.out.println(weakHashMap);
    }

    private static void testPrintMap() {
        Map<String, Integer> params = new HashMap<>();
        //如果缺席word这个key，放一个value值0
        //params.putIfAbsent("word",0);
        //  Integer word = params.get("word");
        //或者放一个默认值0
        Integer word = params.getOrDefault("word", 0);
        System.out.println(word);
        //避免空指针
        params.put("word", word+2);
        System.out.println(params);

        //更优雅的写法，使用merge,取出print的原始值，没有key，默认为0，加上value
        params.merge("print", 1, Integer::sum);
        System.out.println(params);
        params.merge("print", 1, Integer::sum);
        System.out.println(params);
        System.out.println("-------------------------------");
        Set<String> keySet = params.keySet();
        for (String item : keySet) {
            System.out.println("key="+item+",value="+params.get(item));
        }
        System.out.println("-------------------------------");
        Set<Map.Entry<String, Integer>> entries = params.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("key="+key+",value="+value);
        }

        System.out.println("-------------------------------");

        params.forEach((k,v)-> System.out.println("key="+k+",value="+v));
    }

    private static void testBsicUse() {
        Map<String, Employee> map = new HashMap<>();
        map.put("1", new Employee("Java"));
        map.put("2", new Employee("Python"));
        map.put("3", new Employee("Ruby"));
        map.put("4", new Employee("Go"));
        //print all entries
        System.out.println(map);
        //remove an entry
        map.remove("3");
        //replace an entry
        map.put("2", new Employee("C"));
        //look up a value
        map.get("3");
        //set default value
        Employee empty = map.getOrDefault("5", new Employee("empty"));


        System.out.println("look up key is 5 result =" + empty);
        //iterator  through all entries
        map.forEach((k, v) -> {
            System.out.println("key=" + k + ",value=" + v);
        });
    }
}

class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}
