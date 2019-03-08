package com.mycode.juc.future;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 蛮小江
 * 2018/8/19 15:44
 */
public class FutureTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Enter base directory (e.g.  /opt/jdk1.8.0/src):");
            // String directory = in.nextLine();
            String directory ="D:\\devolpment\\install\\jdk\\jdk8_172";
            System.out.println("Enter keyword (e.g.  volatile):");
            //String keyword = in.nextLine();
            String keyword = "refer";
            MatchCounter counter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> task = new FutureTask<>(counter);
            new Thread(task).start();
            try {
                //get阻塞直到获取最终结果
                System.err.println(task.get()+" matching files.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
