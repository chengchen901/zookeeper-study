package com.study.zkclient;

import com.study.zkclient.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author Hash
 * @since 2021/1/3
 */
public class ZkClientClusterTest {

    public static void main(String[] args) {
        // 创建一个zk客户端
        ZkClient client = new ZkClient("localhost:2181,localhost:2182,localhost:2183");
        client.setZkSerializer(new MyZkSerializer());
        try {
            client.createPersistent("/zk/a", true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Random random = new Random();
        new Thread(()->{
            while(true) {
                int idx = random.nextInt(100);
                client.writeData("/zk/a", String.valueOf(idx));
                System.out.println(System.nanoTime()+" 修改/zk/a节点的数据："+idx);
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            while(true) {
                String data = client.readData("/zk/a");
                System.out.println(System.nanoTime()+" 读取到了/zk/a节点数据内容："+data);
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        client.subscribeDataChanges("/zk/a", new IZkDataListener() {

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(System.nanoTime()+" ----收到节点被删除了-------------");
            }

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(System.nanoTime()+" ----收到节点数据变化：" + data + "-------------");
            }
        });

        try {
            Thread.sleep(1000 * 60 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCountDownLath() {
        /*
        * 分布式栅栏实现思路，cdl的value等于信号量，只需要关注cdl的值什么时候变为0即可
        *
        * /cdl
        *   /s1
        *   /s2
        *   /...
        *
        * */
        CountDownLatch cdl = new CountDownLatch(2);

        for(int i = 0; i < 10; i++ ) {
            new Thread(()->{
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }, "thread-"+i).start();
        }

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cdl.countDown();
        System.out.println("预备");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cdl.countDown();
        System.out.println("开始");
    }
}
