package com.menlo.concurrent;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static Integer compute(){
        System.out.println("compute: " + Thread.currentThread());
        //sleep(1L);
        return 2;
    }



    public static void printMessage(Integer value){
        System.out.println(value);
        System.out.println("printMessage: " + Thread.currentThread());
    }
    public static CompletableFuture<Integer> create(){
       return CompletableFuture.supplyAsync(() -> compute());
    }
    public static void main(String[] args) {
        System.out.println("Main: " + Thread.currentThread());
       /*// create().thenAccept(data -> System.out.println(data));
        create().thenAccept (data -> printMessage(data));*/

        CompletableFuture<Integer> cf = create();
        cf.thenAccept(data -> printMessage(data));
    }
    private static void sleep(Long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
