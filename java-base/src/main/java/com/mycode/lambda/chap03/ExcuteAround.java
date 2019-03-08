package com.mycode.lambda.chap03;

import com.google.common.base.Function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * jf
 * 2018/9/27 14:18
 */
public class ExcuteAround {
    public static final String PATH = "lambdasinaction/chap03/data.txt";

    public static void main(String[] args) throws IOException {
        //读取行为，写死的
        String result = processFileLimited();
        System.out.println(result);
        System.out.println("-----");

        //读取一行
        String oneLine = processFile((BufferedReader reader) -> reader.readLine());
        System.out.println(oneLine);

        //读取两行
        String twoLines = processFile(reader -> reader.readLine() + reader.readLine());
        System.out.println(twoLines);

        //Predicate 接口 test() 使用
        Predicate<Integer> predicate = (Integer s) -> s > 0;
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        filter(list, predicate);
        //基本数据类型 拆装箱问题
        //int IntPredicate

        //Consumer使用
        list.forEach(System.out::println);

        //Function 使用
        //String -> Integer
        List<Integer> l = map(Arrays.asList("java", "ruby", "python"), (String s) -> s.length());
        System.out.println(l);

        //boolean
        Predicate<Integer> integerObjectFunction = (Integer i) -> list.add(i);


        //特殊的void兼容规则
        //如果一个lambda的表达式是一个语句，它就和一个返回void的函数描述符兼容（当然需要参数列表也兼容）
        Consumer<Integer> integerObjectFunction1 = (Integer a) -> list.add(a);

        System.out.println("-----------");
        Function<String, Integer> function = Integer::parseInt;
        function = (String str) -> Integer.parseInt(str);
        Integer apply = function.apply("3");
        System.out.println("apply:" + apply);

        BiPredicate<List<String>, String> biFunction = (List<String> data, String element) -> data.contains(element);
        biFunction = List::contains;
        boolean test = biFunction.test(Arrays.asList("java", "ruby", "python"), "java123");
        System.out.println("test:" + test);

        System.out.println("-----------");
        //构造器使用
        Supplier<Object> supplier = Object::new;
        for (int i = 0; i < 10; i++) {
            //get() 每次产生一个 新的对象
            Object s = supplier.get();
            System.out.println(s.hashCode());
        }

        System.out.println("------------");
        l.sort(Comparator.comparing(Integer::intValue));

        System.out.println("------------");
        List<String[]> collect1 = Arrays.asList("Hello", "World").stream()
                .map((str) -> str.split(""))
                .distinct()
                .collect(Collectors.toList());


        String[] arrayOfWords = new String[]{"Hello", "World"};
        //把数组流转换为字符流
        //Arrays.asList("Hello", "World").stream()
        Stream<String> stream = Arrays.stream(arrayOfWords);


        List<String> collect =stream.map((str) -> {
            System.out.println("str:" + str);
            String[] strArray = str.split("");
            System.out.println("length:" + strArray.length + "," + Arrays.asList(strArray));
            return strArray;
        })
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("collect:" +collect);

        System.out.println("------------");
        IntStream.rangeClosed(1,100).filter(i->i%2==0).forEach(System.out::println);

    }

    public static <T, R> List<R> map(List<T> list, java.util.function.Function<T, R> function) {
        List<R> resultList = new ArrayList<R>();
        list.forEach((T t) -> resultList.add(function.apply(t)));
        return resultList;
    }

    private static void filter(List<Integer> list, Predicate<Integer> predicate) {

    }

    private static String processFileLimited() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH)))) {
            return reader.readLine();
        }
    }

    private static String processFile(BufferReaderProcessor processor) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH)))) {
            //调用传递的读取代码逻辑
            return processor.process(reader);
        }
    }

    //读取行行为参数化

    interface BufferReaderProcessor {
        String process(BufferedReader reader) throws IOException;
    }


}
