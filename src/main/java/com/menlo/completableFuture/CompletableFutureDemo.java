package com.menlo.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureDemo {


    public static CompletableFuture<String> create() throws Exception{
        return CompletableFuture.supplyAsync(() -> getMessage());
    }
    public static String getMessage() throws RuntimeException {
        try {
            System.out.println("compute: " + Thread.currentThread());
            TimeUnit.MINUTES.sleep(1);
            return "Hello Aliens";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Main: " + Thread.currentThread());
        CompletableFuture<String> cf = create();
        System.out.println(cf.get(1, TimeUnit.MINUTES));
        cf.complete("complete the future instead of waiting");
        System.out.println(cf.get(1, TimeUnit.MINUTES));
    }

}
