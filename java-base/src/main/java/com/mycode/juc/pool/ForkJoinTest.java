package com.mycode.juc.pool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;

/**
 * 蛮小江
 * 2018/8/27 20:59
 */
public class ForkJoinTest {
    final  static int n = 100000000;//100000000
    public static void main(String[] args) {
        testJava();
        testForkJoin();
    }

    private static void testJava(){
        long start = System.currentTimeMillis();
        Integer [] datas = new Integer [n];
        for (int i = 0; i < n;i++){
            datas[i] = i+1;
        }
        int sum = 0;
        //计算1~10的结果
        List<Integer> nums = Arrays.asList(datas);
        for (Integer num : nums) {
            sum+=num;
        }
        long end = System.currentTimeMillis();
        System.out.println("Java 累加和为="+sum+",累计耗时="+(end-start)/1000);
    }

    private static  void testForkJoin(){
        long start = System.currentTimeMillis();
        Integer [] datas = new Integer [n];
        for (int i = 0; i < n;i++){
            datas[i] = i+1;
        }
        //计算1~10的结果
        List<Integer> nums = Arrays.asList(datas);
        //线程池
        ForkJoinPool pool = new ForkJoinPool();
        //并发安全的累加计数器
        LongAdder longAdder = new LongAdder();
        //初始化任务 RecursiveAction递归操作
        AddTask task = new AddTask(nums, longAdder);
        //提交任务
        pool.invoke(task);
        //关闭资源
        pool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Fork-Join 累加和为="+longAdder.intValue()+",累计耗时="+(end-start)/1000);
    }

    private static class AddTask extends RecursiveAction {
        private final List<Integer> nums;
        private final LongAdder longAdder;

        public AddTask(List<Integer> nums, LongAdder longAdder) {
            this.nums = nums;
            this.longAdder = longAdder;
        }

        @Override
        protected void compute() {
            //拿到size
            int size = nums.size();

            //判断parts
            if (size > 1) {
                //二分法处理
                int parts = size / 2;
                //分割lList
                //左边部分
                List<Integer> leftPar = nums.subList(0, parts);
                //递归
                AddTask leftTask = new AddTask(leftPar, longAdder);
                //右边部分
                List<Integer> rightPar = nums.subList(parts, size);
                AddTask rigthTask = new AddTask(rightPar, longAdder);
                //执行For-Join操作
                invokeAll(leftTask, rigthTask);
            } else {
                if (size == 0) {
                    return;
                }
                Integer indexNum = nums.get(0);
                longAdder.add(indexNum);
            }
        }
    }
}
