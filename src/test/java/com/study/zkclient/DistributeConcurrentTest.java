package com.study.zkclient;

import com.study.originalclient.OrderServiceImplWithDistributeLock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Hash
 * @since 2021/1/3
 */
public class DistributeConcurrentTest {

    public static void main(String[] args) {
        // 服务集群数
        int service = 5;
        // 并发数
        int requestSize = 10;

        CyclicBarrier requestBarrier = new CyclicBarrier(requestSize * service);
        // 多线程模拟高并发
        for (int i = 0; i < service; i++) {
            new Thread(new Runnable() {
                public void run() {
                    // 模拟分布式集群的场景
                    OrderServiceImplWithDistributeLock orderService = new OrderServiceImplWithDistributeLock();

                    System.out.println(Thread.currentThread().getName() + "---------我准备好---------------");

                    for(int i =0; i < requestSize; i++) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 等待service台服务，requestSize个请求 一起出发
                                    requestBarrier.await();
                                } catch (InterruptedException | BrokenBarrierException e) {
                                    e.printStackTrace();
                                }
                                orderService.createOrder();
                            }

                        }).start();
                    }
                }
            }).start();

        }

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
