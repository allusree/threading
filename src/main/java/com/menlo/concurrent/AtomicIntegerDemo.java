package com.menlo.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(100);

        System.out.println(atomicInteger.addAndGet(2));         //102
        System.out.println(atomicInteger);                      //102

        System.out.println(atomicInteger.getAndAdd(2));         //102
        System.out.println(atomicInteger);                      //104

        System.out.println(atomicInteger.incrementAndGet());    //105
        System.out.println(atomicInteger);                      //105

        System.out.println(atomicInteger.getAndIncrement());    //105
        System.out.println(atomicInteger);                      //106

        System.out.println(atomicInteger.decrementAndGet());    //105
        System.out.println(atomicInteger);                      //105

        System.out.println(atomicInteger.getAndDecrement());    //105
        System.out.println(atomicInteger);                      //104


        //Compare and swap operations
        atomicInteger.set(100);
        boolean isSuccess = atomicInteger.compareAndSet(100,110);   //current value 100

        System.out.println(isSuccess);      //true

        isSuccess = atomicInteger.compareAndSet(100,120);       //current value 110

        System.out.println(isSuccess);      //false
    }
}
