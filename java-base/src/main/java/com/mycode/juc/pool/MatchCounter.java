package com.mycode.juc.pool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 蛮小江
 * 2018/8/19 15:44
 */
public class MatchCounter implements Callable<Integer> {
    private File directory;
    private  String keyword;
    private ExecutorService pool;

    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        List<Future<Integer>> results = new ArrayList<>();
        File[] files = directory.listFiles();
        for(File file :files){
               if(file.isDirectory()){
                   MatchCounter counter = new MatchCounter(file, keyword,pool);
                   Future<Integer> result = pool.submit(counter);
                   results.add(result);
               }else{
                   if(search(file)){
                         count++;
                   }
               }
        }

        //把所有结果加起来汇总
        for (Future<Integer> result : results){
            //阻塞直到有结果返回
            count+= result.get();
        }
        return  count;
    }

    private boolean search(File file) {
        try (Scanner in = new Scanner(file, "UTF-8")) {
            boolean found = false;
            while(!found && in.hasNext()){
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    found =  true;
                }
            }
            return found;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }
}
