package com.mycode.designpattern.chain.reponsibility;

import java.util.LinkedList;
import java.util.List;

/**
 * 蛮小江
 * 2018/8/23 8:22
 */
public class ChainDemo {
    public static void main(String[] args) {
        DefaultExcutorChain chain = new DefaultExcutorChain();
        chain.addExecutor(new Executor() {
            @Override
            public void excute(ExecutorChain chain) {
                System.out.println(Thread.currentThread().getName()+" "+"Hello World");
               chain.excute();
            }
        });
        chain.addExecutor(new Executor() {
            @Override
            public void excute(ExecutorChain chain) {
                System.out.println(Thread.currentThread().getName()+" "+"AAAAA");
            }
        });
        chain.excute();
        System.out.println(Thread.currentThread().getName()+" "+"执行完成");
    }

    static class DefaultExcutorChain implements ExecutorChain {
        private List<Executor> executorList = new LinkedList<>();

        public void addExecutor(Executor executor) {
            executorList.add(executor);
        }

        private int position;

        @Override
        public void excute() {
            int pos = position;
            Executor executor = executorList.get(pos);
            position++;
            System.out.println("执行第 " + pos + " 个Executor元素");
            executor.excute(this);

        }
    }

    interface Executor {
        void excute(ExecutorChain chain);
    }

    interface ExecutorChain {
        void excute();
    }
}
