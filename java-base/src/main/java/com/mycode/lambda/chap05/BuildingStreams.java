package com.mycode.lambda.chap05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * jf
 * 2018/10/2 17:56
 */
public class BuildingStreams {
    public static void main(String[] args) {
        //Stream.of  通过值 方式 构建
        Stream<String> stream = Stream.of("java", "python", "c", "c+_");
        //转换为大写，再输出
        stream.map(String::toUpperCase)
                .forEach(System.out::println);

        //构建一个空的流字符
        Stream<String> emptyStream = Stream.empty();

        //Arrays.stream
        int[] numbers = {2, 3, 5, 7, 11, 13};
        System.out.println(Arrays.stream(numbers));
        //Arrays.stream(numbers).forEach(System.out::println);


        //通过访问文件的方式构建流
        try {
            //Stream<String>
          long uniqueWords =   Files.lines(Paths.get("D:\\devolpment\\java\\workspace\\idea\\mycode\\java-base\\src\\main\\resources\\lambdasinaction\\chap05\\data.txt"), Charset.defaultCharset())
            .flatMap((String line)->Arrays.stream(line.split("")))
            .distinct()
            .count();
            System.out.println("There are "+uniqueWords+" unique words in data.txt");


        } catch (IOException e) {
            e.printStackTrace();
        }


        //通过函数的方式创建流
        System.out.println("---");
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        //斐波那契数列
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        System.out.println("---");
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        System.out.println("---");
        //生成是个随机数
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        //Supplier接口
        //generate 使用 一直返回1
        System.out.println("---");
        IntStream.generate(() -> 1)
                .limit(5)
                .forEach(System.out::println);

        System.out.println("---");
        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5)
                .forEach(System.out::println);

        System.out.println("---");
        //有状态的类来记录
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current  = 1;
            @Override
            public int getAsInt() {
                int nextValue = this.previous + this.current;
                this.previous  = this.current;
                this.current = nextValue;
                return previous;
            }
        };
        IntStream.generate(fib)
                .limit(10)
                .forEach(System.out::println);
    }


}
