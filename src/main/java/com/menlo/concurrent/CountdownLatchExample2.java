package com.menlo.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CountdownLatchExample2 {

    public static void main(String[] args) {
        try {
            CountDownLatch latch = new CountDownLatch(6);
            ExecutorService executerService = Executors.newFixedThreadPool(3);
            List<Future<String>> fututeList = new ArrayList<Future<String>>();
            IntStream.range(0, 6).forEach(id -> {
                fututeList.add(
                        executerService.submit(() -> {
                            System.out.println("Thread " + id + " Going to sleep");

                            if(id == 4){
                                Thread.sleep(5000);
                            } else{
                                Thread.sleep(1000);
                            }
                            System.out.println("Thread " + id + " Awake to sleep");
                            latch.countDown();
                            String message = "Random String to process in future";
                            return message.split(" ")[id];
                        }));
            });
            latch.await();
            executerService.shutdownNow();
            fututeList.forEach(future ->{
                try {
                    System.out.println(future.get(10, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            System.out.println(Thread.currentThread().getName() +
                    " has finished");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
