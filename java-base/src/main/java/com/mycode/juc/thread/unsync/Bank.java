package com.mycode.juc.thread.unsync;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 蛮小江
 * 2018/8/16 14:43
 */
public class Bank {
    private final double [] accounts;
    private ReentrantLock lock ;
    private Condition sufficientFunds;

    public Bank(int n ,double initialBalance) {
        this.accounts = new double[n];
        Arrays.fill(accounts,initialBalance);
        lock = new ReentrantLock();
        //获取了锁，要判断条件是否满足，不满足进入阻塞状态，需要其他线程唤醒
        sufficientFunds = lock.newCondition();
    }

    public   void  transfer(int from ,int to ,double amount){
        lock.lock();
        try {
            doTransfer(from, to, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public  synchronized  void  transfe3r(int from ,int to ,double amount){

        try {
            doTransfer(from, to, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public void  transfer2(int from ,int to ,double amount){
         lock.lock();
        try {
            doTransfer(from, to, amount);
        } catch (Exception e) {
                  e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private   void doTransfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount) {
            //不满足条件，阻塞
            sufficientFunds.await(); ;
        }
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf("%10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf("Total balance : %10.2f %n", getTotalBalance());
        //解除该条件的等待集合的所有线程的阻塞状态
        sufficientFunds.notifyAll(); ;
    }

    private synchronized void doTransfer1(int from, int to, double amount) throws InterruptedException {
        if (accounts[from] < amount) {
            wait();
        }
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf("%10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf("Total balance : %10.2f %n", getTotalBalance());
        notifyAll(); ;
    }

    //锁对象
    public void  transfer1(int from ,int to ,double amount) throws InterruptedException {
        synchronized (Bank.class) {
                      doTransfer(from, to, amount);
        }
    }

    public double getTotalBalance() {
        double sum = 0;
        for (double a : accounts) {
            sum += a;
        }
        return sum;
    }

    public int size(){
        return  accounts.length;
    }
}
