package com.mycode.juc.pool;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * 蛮小江
 * 2018/8/19 16:19
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter base directory (e.g.  /opt/jdk1.8.0/src):");
            // String directory = in.nextLine();
            String directory ="D:\\devolpment\\install\\jdk\\jdk8_172";
            System.out.println("Enter keyword (e.g.  volatile):");
            //String keyword = in.nextLine();
            String keyword = "refer";
            ExecutorService pool = Executors.newCachedThreadPool();
            MatchCounter counter = new MatchCounter(new File(directory), keyword,pool);
            Future<Integer> result = pool.submit(counter);

            try {
                //get阻塞直到获取最终结果
                System.err.println(result.get()+" matching files.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            pool.shutdown();


            int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("large pool size="+largestPoolSize);
        }
    }
}
