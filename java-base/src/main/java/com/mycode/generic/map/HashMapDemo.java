package com.mycode.generic.map;

import java.util.*;

/**
 * 蛮小江
 * 2018/8/9 8:37
 */
class A {

}

class AClass extends A {
    public AClass() {
    }
}

class B extends AClass {

}




public class HashMapDemo {

    class InnerClass {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void testA() {
        List<AClass> strs = new ArrayList<AClass>();
        strs.add(new AClass());
        inspectA(strs); //

        List<A> strs1 = new ArrayList<A>();
        strs1.add(new A());
    }

    public static void inspectA(List<? super AClass> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }

        list.add(new AClass());
        list.add(new B());
        //  list.add(new A());//编译不通过
       // list.add(new Object());//编译不通过
        
        //取数据  只能是Object，存在类型丢失
        Object object = list.get(0);
       // AClass aClass =  list.get(0);//编译不通过
    }

    public static void inspectB(List<? extends AClass> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
        //原因是编译器只知道容器内是AClass或者它的派生类
        //  list.add(new A());//编译不通过
       // list.add(new Object());//编译不通过
        //取值 AClass 或者AClass的父类 A 、Object
        AClass b = list.get(0);
        A aClass = list.get(0);
        Object o = list.get(0);
        //泛型上限限定，引用赋值
        ArrayList<AClass> aClassesList = new ArrayList<>();
        List<? extends A> Alist = aClassesList;
    }

    public void inspect(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
        // list.add(1); //这个操作在当前方法的上下文是合法的。
    }

    public void test() {
        List<String> strs = new ArrayList<String>();
        inspect(strs); //编译错误
    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "hello,world");
        map.put("hobby", "read book");
        map.put("info", "others");
        System.out.println(map);

        print("name:hello world", "hobby:read book", "info:others");

        testAutoBox(10);


    }

    public enum SIZE {
        SMALL("S"), MIDDLE("M"), LARGE("L");
        private final String descript;

        private SIZE(String descript) {
            this.descript = descript;
        }

        public String getDescript() {
            return descript;
        }
    }

    public static void testAutoBox(int num) {
        //装箱
        Integer n = num;
        //拆箱
        int k = n;
    }

    public static void print(String... strs) {
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
        }
    }
}
