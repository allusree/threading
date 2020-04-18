package com.menlo.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        Thread t1 = new Thread(() -> {
            try {
                //Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()
                        + " finished");
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                //Thread.sleep(2000);
                latch.countDown();
                System.out.println(Thread.currentThread().getName()
                        + " finished");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                latch.countDown();
                System.out.println(Thread.currentThread().getName()
                        + " finished");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




        t1.start();
        t2.start();
        t3.start();

        // The main task waits for four threads
        latch.await();

        // Main thread has started
        System.out.println(Thread.currentThread().getName() +
                " has finished");


    }
}
