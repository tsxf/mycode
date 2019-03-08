package com.mycode.zookeeper.basic;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingCluster;
import org.apache.curator.test.TestingServer;
import org.apache.curator.test.TestingZooKeeperServer;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 蛮小江
 * 2018/6/14 13:39
 */
public class CuratorSimpleUseDemo {
    private final static String Connnection_String = "192.168.137.137:2181,192.168.137.133:2181,192.168.137.138:2181";
    static CuratorFramework client;
    private static String path = "/zk-curator";
    private static String path_nodeCache = "/zk-curator/nodecache";
    private static String path_master = "/zk-master";
    private static String path_distatomicint = "/zk-distatomic";
    private static CountDownLatch semaphore = new CountDownLatch(2);
    private static ExecutorService tp = Executors.newFixedThreadPool(2);
    public static CyclicBarrier barrier = new CyclicBarrier(3);

    private static void init() {
        client = CuratorFrameworkFactory.builder()
                .connectString(Connnection_String)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        client.start();
    }

    public static void main(String[] args) throws Exception {

        //初始化zookeeper
        init();
        System.out.println("Main thread :" + Thread.currentThread().getName());

        //异步创建节点，使用线程池
        //  zk_create();

        //事件监听
      //  eventWatched();

        //Master选举
        //  masterSelect();

        //分布式锁
        distLock();

        //分布式计时器
        // distCount();

        //分布式barrier
        // distBarrier();


        //工具类的使用
        //1.ZKPaths
        //zkPath();

        //2.EnsurePath

        //client.usingNamespace(path);


        //TestingServer
        //没有成功
        //testingServer();

        //TestingCluster
        //没有成功
        //  testCluster();

        return;

    }

    private static void testCluster() throws Exception {
        TestingCluster cluster = new TestingCluster(3);
        cluster.start();
        Thread.sleep(2000);
        TestingZooKeeperServer leader = null;
        for (TestingZooKeeperServer zs : cluster.getServers()) {
            System.out.print(zs.getInstanceSpec().getServerId() + "-");
            System.out.print(zs.getQuorumPeer().getServerState() + "-");
            System.out.print(zs.getInstanceSpec().getDataDirectory().getAbsoluteFile());
            if (zs.getQuorumPeer().getServerState().equals("leading")) {
                leader = zs;
            }
        }

        leader.kill();
        System.out.println("---After leader kill .....");

        for (TestingZooKeeperServer zs : cluster.getServers()) {
            System.out.print(zs.getInstanceSpec().getServerId() + "-");
            System.out.print(zs.getQuorumPeer().getServerState() + "-");
            System.out.print(zs.getInstanceSpec().getDataDirectory().getAbsoluteFile());
        }

        cluster.stop();
    }

    private static void testingServer() throws Exception {
        String path_testpath = "/zookeeper";
        TestingServer server = new TestingServer(2181, new File("/opt/zookeeper/data"));
        CuratorFrameworkFactory.builder()
                .connectString(server.getConnectString())
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();

        System.out.println(client.getChildren().forPath(path_testpath));
        server.close();
    }

    private static void zkPath() throws Exception {
        String path_zkpath = "/curator_zkpath";
        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();

        //显示节点信息
        System.out.println(ZKPaths.fixForNamespace(path_zkpath, "/sub"));///curator_zkpath/sub
        System.out.println(ZKPaths.makePath(path_zkpath, "/sub"));///curator_zkpath/sub
        System.out.println(ZKPaths.getNodeFromPath(path_zkpath + "/sub1"));//sub1

        ZKPaths.PathAndNode pn = ZKPaths.getPathAndNode(path_zkpath + "/sub1");
        System.out.println(pn.getPath());///curator_zkpath
        System.out.println(pn.getNode());//sub1


        //创建节点
        String dir1 = path_zkpath + "/child1";
        String dir2 = path_zkpath + "/child2";

        ZKPaths.mkdirs(zooKeeper, dir1);
        ZKPaths.mkdirs(zooKeeper, dir2);
        System.out.println(ZKPaths.getSortedChildren(zooKeeper, path_zkpath));//[child1, child2]
        Thread.sleep(10000);
        ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), path_zkpath, true);
    }

    private static void distBarrier() throws Exception {
        //常规使用
        //jdkBarrier();
        //分布式barrier，有主线程触发和释放barrier
         distributeBarrier();
        //分布式barrier，各自线程触发和释放barrier
        distributerDoubleBarrier();
    }

    private static void distributerDoubleBarrier() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(client, path, 5);
                    try {
                        Thread.sleep(Math.round(Math.random() * 3000));
                        System.out.println(Thread.currentThread().getName() + "号进入了barrier设置");
                        //启动
                        //enter()方法之后进行等待，此时处于准备进入状态，一旦进入Barrier的成员变量达到5个后，
                        // 所有的成员会同时触发进入
                        distributedDoubleBarrier.enter();
                        System.out.println(Thread.currentThread().getName() + "号启动....");
                        Thread.sleep(Math.round(Math.random() * 3000));
                        //释放
                        //leave()方法会再次进入等待，此时处于准备退出状态，一旦准备退出的数量达到5个后，
                        // 所有的成员会同时触发退出
                        distributedDoubleBarrier.leave();
                        System.out.println(Thread.currentThread().getName() + "号退出....");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    private static void distributeBarrier() throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    distributedBarrier = new DistributedBarrier(client, path);
                    System.out.println(Thread.currentThread().getName() + "号设置了barrier");
                    try {
                        //完成对barrirer的设置
                        distributedBarrier.setBarrier();
                        //等待barrier的释放
                        distributedBarrier.waitOnBarrier();

                        System.out.println(Thread.currentThread().getName() + "启动了....");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        Thread.sleep(2000);
        //释放barrirer,同时触发所有等待该Barrier的5个线程同时进行各自的业务逻辑处理
        distributedBarrier.removeBarrier();
    }

    static DistributedBarrier distributedBarrier;

    private static void jdkBarrier() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Runner("1号选手"));
        executorService.submit(new Runner("2号选手"));
        executorService.submit(new Runner("3号选手"));

        executorService.shutdown();
    }

    static class Runner implements Runnable {
        private String name;

        public Runner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " 准备好了");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + " 起跑了");
        }
    }

    private static void distCount() throws Exception {
        //1.统计在线人数 利用DistributedAtomicInteger
        DistributedAtomicInteger atomicInteger =
                new DistributedAtomicInteger(client, path_distatomicint, new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("Result:" + rc.succeeded() + ",state:" + rc.getStats() + ",preValue:" + rc.preValue() + ",postValue:" + rc.postValue());
        System.in.read();
    }

    private static void distLock() {
        //1.模拟产生一个并发问题
        // simulatJUC();
        //2. 使用curator的InterProcessMutes
        interProcessMutex();
    }

    private static void interProcessMutex() {
        final InterProcessMutex lock = new InterProcessMutex(client, path_master);

    //    final CountDownLatch down = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                     //   down.await();
                        lock.acquire();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSSSS");
                        String orderNo = sdf.format(new Date());
                        System.out.println("生成的订单编号是：" + orderNo);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        }
        // down.await();
       // down.countDown();
    }

    private static void simulatJUC() throws InterruptedException {
        final CountDownLatch down = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        down.await();
                        //  down.countDown();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSSSS");
                        String orderNo = sdf.format(new Date());
                        System.out.println("生成的订单编号是：" + orderNo);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        // down.await();
        down.countDown();

    }

    private static void masterSelect() throws InterruptedException, IOException {
        //Master选举
        LeaderSelector selector = new LeaderSelector(client, path_master, new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为Master角色");
                //模拟业务处理
                Thread.sleep(3000);
                System.out.println("完成Master,释放Master角色");
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
                System.out.println("stateChanged触发......");
                System.out.println("client:" + client + ",state:" + state.name() + "," + state.isConnected());
            }
        });

        selector.autoRequeue();
        selector.start();
       /* Thread.sleep(10000);
        selector.close();*/

        System.in.read();
    }

    private static void eventWatched() throws Exception {
        //1.节点监听 NodeCache
        //nodeCaceUse();
        //2.子节点监听 PathChildrenCache
        pathChildrenCache();
    }

    private static void pathChildrenCache() throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADD," + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED," + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED," + event.getData().getPath());
                        break;
                    default:
                        break;
                }
            }
        });

        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path);
        Thread.sleep(1000);
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path + "/c1");

        Thread.sleep(1000);
        client.setData().forPath(path + "/c1", "update223".getBytes());
        Thread.sleep(1000);

        //对于二级子节点不会触发监听事件
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path + "/c1/c2");

        //下面两句的执行效果是一样的，不能删除path节点
        // client.delete().deletingChildrenIfNeeded().forPath(path + "/c1");
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        System.in.read();
    }

    private static void nodeCaceUse() throws Exception {
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path_nodeCache, "init".getBytes());
        final NodeCache nodeCache = new NodeCache(client, path_nodeCache);
        //是否立即从节点获取数据
        nodeCache.start(true);
        //添加监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Node data update,new data:" + new String(nodeCache.getCurrentData().getData()));
            }
        });
        //更新
        client.setData().forPath(path_nodeCache, "update".getBytes());
        Thread.sleep(1000);
        //删除数据
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path_nodeCache);
        //阻塞
        System.in.read();
    }

    private static void zk_create() throws Exception {
        //创建一个节点，同时使用自定义的线程池
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("event[code:" + curatorEvent.getResultCode() + ",type:" + curatorEvent.getType() + "]");
                        System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                        Thread.sleep(3000);
                        semaphore.countDown();
                    }
                }, tp)
                .forPath(path, "init".getBytes());

        //再次创建这个节点
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("event[code:" + curatorEvent.getResultCode() + ",type:" + curatorEvent.getType() + "]");
                        System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                        semaphore.countDown();
                    }
                }).forPath(path, "u".getBytes());

        //阻塞
        semaphore.await();
        tp.shutdown();
    }


}
