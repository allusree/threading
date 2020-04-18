package com.menlo.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CyclicBarrierExample {

    public void example() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            // Task
            System.out.println("All previous tasks are completed");
        });

        Thread t1 = new Thread(() ->{
            try {
                System.out.println("Thread : " + Thread.currentThread().getName() + " is waiting");
                cyclicBarrier.await();
                System.out.println("Thread : " + Thread.currentThread().getName() + " is released");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        Thread t2 = new Thread(() ->{
            try {
                System.out.println("Thread : " + Thread.currentThread().getName() + " is waiting");
                cyclicBarrier.await();
                System.out.println("Thread : " + Thread.currentThread().getName() + " is released");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() ->{
            try {
                System.out.println("Thread : " + Thread.currentThread().getName() + " is waiting");
                cyclicBarrier.await();
                System.out.println("Thread : " + Thread.currentThread().getName() + " is released");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        if (!cyclicBarrier.isBroken()) {
            t1.start();
            t2.start();
            t3.start();
        }
    }




    public void threadCallableInvoke() throws Exception {
        Callable<String> callableTask = () -> {
            System.out.println("Timer " + Thread.currentThread().getName());
            //  TimeUnit.MILLISECONDS.sleep(50);
            return "Task's execution";
        };


        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = executorService.invokeAll(callableTasks);
        String invokeAnyResult = executorService.invokeAny(callableTasks);
        executorService.shutdownNow();
        //System.out.println("invokeAnyResult : " + invokeAnyResult);
        futures.forEach(future -> {
            try {

                System.out.println(future.isDone());
                System.out.println(future.isCancelled());
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }









}
