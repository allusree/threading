package com.menlo.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletableFutureInAction {
    public static void main(String[] args) throws Exception {
        //thenApplyDemo();

        //sequence of transformations , series of thenApply
        thenApplySeriesDemo();

        //thenAcceptDemo();
        //thenRunDemo();

    }


    private static void thenAcceptDemo() throws Exception{
        //access to the result of the CompletableFuture on which it is attached.
        System.out.println("In thenAcceptDemo" + Thread.currentThread().getName());
        CompletableFuture<Void> acceptFuture = getMessage().thenAccept(System.out::println);
        acceptFuture.get();
    }

    private static void thenRunDemo()throws Exception {
        System.out.println("thenRunDemo " + Thread.currentThread().getName());
        // no access to the result of the CompletableFuture on which it is attached.
        CompletableFuture<Void> acceptFuture = getMessage().thenRun(System.out::println);
        acceptFuture.get();
    }

    private static void thenApplyDemo() throws Exception {
        System.out.println("thenApplyDemo " + Thread.currentThread().getName());
        CompletableFuture<String> applyFuture = getMessage().thenApply(message -> message + "Please go");
        System.out.println(applyFuture.get());
    }

    private static void thenApplySeriesDemo() throws Exception {
        System.out.println("In thenApplySeriesDemo" + Thread.currentThread().getName());
        CompletableFuture<String> multiApplyFuture = getMessage().thenApply(message -> message + "Please go")
                .thenApply(msg -> msg + " to China").thenApply(msg -> msg.toUpperCase());
        System.out.println(multiApplyFuture.get());
    }

    private static CompletableFuture<String> getMessage() {
        System.out.println("In getMessage" + Thread.currentThread().getName());
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Hey Corona !!! ";
        });
        return future;
    }
}
