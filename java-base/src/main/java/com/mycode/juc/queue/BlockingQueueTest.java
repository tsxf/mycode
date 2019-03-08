package com.mycode.juc.queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 蛮小江
 * 2018/8/19 14:11
 */
public class BlockingQueueTest {
    private final static int FILE_QUEUE_SIZE = 10;
    private final static int SEARCH_THREADS = 100;
    //队列元素结束（为空）的标志
    private final static File DUMPY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter base directory (e.g.  /opt/jdk1.8.0/src):");
           // String directory = in.nextLine();
            String directory ="D:\\devolpment\\install\\jdk\\jdk8_172";
            System.out.println("Enter keyword (e.g.  volatile):");
            //String keyword = in.nextLine();
            String keyword = "refer";
            Runnable enumerator = () -> {
                try {
                    enumerator(new File(directory));
                    queue.put(DUMPY);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            };
            //启动线程，遍历 目录下面所有的文件，放入队列中
            new Thread(enumerator).start();

            //启动搜索线程
            for (int i = 1; i <= SEARCH_THREADS; i++) {
                Runnable search = () -> {
                    try {
                        boolean done = false;
                        while (!done) {

                                File file = queue.take();
                                //队列为空
                                if(file == DUMPY){
                                    //告诉其他线程，队列为空，结束该搜索线程
                                    queue.put(file);
                                    done = true;
                                }else{
                                    //搜索文件中含有的关键字
                                search(file,keyword);
                            }

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                };

                new Thread(search).start();
            }
        }
    }

    private static void search(File file, String keyword) throws FileNotFoundException {
        try(Scanner in = new Scanner(file,"UTF-8")) {
             int lineNumber = 0;
            while (in.hasNext()) {
                lineNumber++;
                String line = in.nextLine();
                if(line.contains(keyword)){
                    System.out.printf("%s:%d:%s%n",file.getPath(),lineNumber,line);
                }

            }
        }
    }

    private static void enumerator(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerator(file);
            } else {
                queue.put(file);

            }
        }


    }
}
