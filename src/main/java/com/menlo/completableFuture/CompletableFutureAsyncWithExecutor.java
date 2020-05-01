package com.menlo.completableFuture;

import java.util.concurrent.*;

public class CompletableFutureAsyncWithExecutor {
    public static void main(String[] args) throws Exception {
        System.out.println("Main: " + Thread.currentThread());
        //Future<Void> future = runAsyncRunnable();
        //future.get();

        Future<String> future1 = supplyAsyncRunnable();
        System.out.println(future1.get());
    }


    private static Future<Void>  runAsyncRunnable() {
        Executor executor = Executors.newCachedThreadPool();
        Future<Void> future = CompletableFuture.runAsync(() -> {
            // Simulate a long-running Job
            try {
                System.out.println("In runAsyncRunnable" + Thread.currentThread());
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        }, executor);
        return future;
    }

    private static Future<String>  supplyAsyncRunnable() {
        Executor executor = Executors.newCachedThreadPool();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("In supplyAsyncRunnable" + Thread.currentThread());
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        }, executor);
        return future;
    }









}
