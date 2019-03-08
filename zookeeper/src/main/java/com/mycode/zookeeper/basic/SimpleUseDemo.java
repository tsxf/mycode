package com.mycode.zookeeper.basic;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 使用同步的方式操作Zookeeper
 */
public class SimpleUseDemo implements  Watcher{
    static CountDownLatch countDownLatch = new CountDownLatch(1);
    private final static String Connnection_String = "192.168.137.137:2181,192.168.137.133:2181,192.168.137.138:2181";
    static ZooKeeper zooKeeper = null;

    //初始化zookeeper
    private  static void initZookeeper() throws IOException {
    /*    zooKeeper = new ZooKeeper(Connnection_String, 4000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
                System.out.println("event:" + watchedEvent.getType() + ",watchedEvent:" + watchedEvent);
            }
        });*/
        zooKeeper = new ZooKeeper(Connnection_String, 4000, new SimpleUseDemo());
    }

    //复用zookeeperr的会话
    private  static  ZooKeeper initZookeeeperWithSessionId(long sessionId,byte [] sessionPasswd) throws IOException {
        return zooKeeper = new ZooKeeper(Connnection_String,4000,new SimpleUseDemo(),sessionId,sessionPasswd);
    }


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        initZookeeper();
        countDownLatch.await();
        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();
        zooKeeper = initZookeeeperWithSessionId(1224,"test".getBytes());
        zooKeeper = initZookeeeperWithSessionId(sessionId,sessionPasswd);
        //阻塞等待
        countDownLatch.await();
        //获取zookeeper的状态
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("state:" + state);
        //使用持久化节点
        invokePersistentNode();

        //异步调用创建节点
       // invokeAsyncNode();
        //阻塞
        System.in.read();

    }

    //异步调用
    private static  void  invokeAsyncNode(){
        String nodeName = "/zk_ephemeral_list_";
        zooKeeper.create(nodeName, "1".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL, new IStringCallback(), "I am context");
        zooKeeper.create(nodeName, "1".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL, new IStringCallback(), "I am context");
        zooKeeper.create(nodeName, "1".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context");
    }

    private static void invokePersistentNode() throws KeeperException, InterruptedException {
        //创建节点
        String nodeName = "/zk_persistent_node";
        createNode(nodeName);
        //获取节点信息
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData(nodeName, null, stat);
        System.out.println("get node data  :" + new String(data));
        //修改节点信息
        modifyNode(nodeName, "2", stat.getVersion());
        zooKeeper.getData(nodeName, null, stat);

        //在nodeName节点下创建临时节是c1
        zooKeeper.create(nodeName + "/c1", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        //获取节点nodeName下面的数据
        //同步的方式
       /* List<String> childrenList = zooKeeper.getChildren(nodeName, true);
        System.out.println(childrenList);*/

        //异步的方式
        zooKeeper.getChildren(nodeName,true, new AsyncCallback.Children2Callback() {
            @Override
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                System.out.println("Get children znode result:[response code:"+rc+",param path:"+path+",ctx:"+ctx+",children list:"+children+",stat:"+stat);
            }
        }, null);

        //在nodeName节点下创建持久化节点c2
        zooKeeper.create(nodeName + "/c2", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        //删除节点
       // delete(nodeName, stat.getVersion());

    }

    private static void close() throws InterruptedException {
        zooKeeper.close();
    }

    private static void createNode(String nodeName) throws KeeperException, InterruptedException {
        String path = zooKeeper.create(nodeName, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("create node return :" + path);
    }


    private static void modifyNode(String nodeName, String value, int version) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.setData(nodeName, value.getBytes(), version);
        System.out.println("modify node " + nodeName + ",return value:" + stat);
    }

    private static void delete(String nodeName, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(nodeName, version);
        System.out.println("node " + nodeName + ",success deleted...");
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if(watchedEvent.getType()== Event.EventType.None&& null==watchedEvent.getPath()){
                System.out.println(".............");
                countDownLatch.countDown();
            }else if(watchedEvent.getType()== Event.EventType.NodeChildrenChanged){
                try {
                    System.out.println("ReGet Child:"+zooKeeper.getChildren(watchedEvent.getPath(),true));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if (watchedEvent.getType()== Event.EventType.NodeDataChanged){
                try {
                    Stat stat = new Stat();
                    System.out.println(new String(zooKeeper.getData(watchedEvent.getPath(),true,stat))+"");
                    System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
     //   System.out.println("Receive watched event:" + watchedEvent);
    }

    static class IStringCallback implements AsyncCallback.StringCallback{

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("Create path result:["+rc+","+path+","+ctx+",real path name:"+name);
        }
    }
}
