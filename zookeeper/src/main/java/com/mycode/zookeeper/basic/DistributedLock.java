package com.mycode.zookeeper.basic;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 蛮小江
 * 2018/6/18 21:10
 */
public class DistributedLock implements Lock, Watcher {
    private final static String Connnection_String = "192.168.137.137:2181,192.168.137.133:2181,192.168.137.138:2181";
    private ZooKeeper client = null;
    private String ROOT = "/locks";//定义根节点
    private String WAIT_LOCK = "";//等待前一个锁
    private String CURRENT_LOCK = "";//当前锁
    private   CountDownLatch countDownLatch = null;

    public DistributedLock() {
        try {
            client = new ZooKeeper(Connnection_String, 4000, this);
            Stat stat = client.exists(ROOT, false);
            if (stat == null) {
                client.create(ROOT, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void lock() {
        if(tryLock()){
            System.out.println(Thread.currentThread().getName()+"获得锁成功");
        }
        //没有获得锁，继续等待锁
        waitForLock( WAIT_LOCK);

    }

    private  boolean waitForLock(String prev){
        try {
            Stat stat = client.exists(prev,true);
            if(stat != null){
                System.out.println(Thread.currentThread().getName()+"等待锁："+CURRENT_LOCK+",释放锁："+prev);
                countDownLatch   = new CountDownLatch(1);
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+"获得了锁");
                return  true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock() {

        try {
            CURRENT_LOCK = client.create(ROOT + "/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = client.getChildren(ROOT, false);
            SortedSet<String> sortedSet = new TreeSet<String>();
            for (String node : children) {
                sortedSet.add(ROOT+"/"+node);
            }
            String firstNode = sortedSet.first();
            SortedSet<String> lessThanCurrent = sortedSet.headSet(CURRENT_LOCK);
            if (CURRENT_LOCK.equals(firstNode)) {
                return true;
            }
            if (!lessThanCurrent.isEmpty()) {
                WAIT_LOCK = lessThanCurrent.last();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
           if(countDownLatch!=null){
               countDownLatch.countDown();
           }
    }

    public static void main(String[] args) throws IOException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0 ; i <10; i++){
            new Thread(()->{
                try {
                    countDownLatch.await();
                    DistributedLock distributedLock = new DistributedLock();
                    distributedLock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"Thead-"+i).start();
            countDownLatch.countDown();
        }

        System.in.read();
    }
}
