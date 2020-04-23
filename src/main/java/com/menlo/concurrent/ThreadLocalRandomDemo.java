package com.menlo.concurrent;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomDemo {

    public static void main(String[] args) {

        System.out.println("Random int:");
        // Returns a pseudorandom, uniformly distributed integer value between 0
        // (inclusive) and 10000 (exclusive)
        System.out.println(ThreadLocalRandom.current().nextInt(10000));

        // Returns a pseudorandom, uniformly distributed int value between 5000
        // (inclusive) and 10000 (exclusive)
        System.out.println(ThreadLocalRandom.current().nextInt(5000, 10000));

        System.out.println("Random long:");
        // Returns a pseudorandom, uniformly distributed long value between 0
        // (inclusive) and 10000 (exclusive)
        System.out.println(ThreadLocalRandom.current().nextLong(10000));

        // Returns a pseudorandom, uniformly distributed long value between 5000
        // (inclusive) and 10000 (exclusive)
        System.out.println(ThreadLocalRandom.current().nextLong(5000, 10000));

        System.out.println("Random double:");
        // Returns a pseudorandom, uniformly distributed long value between 0
        // (inclusive) and 10000 (exclusive)
        System.out.println(ThreadLocalRandom.current().nextDouble(10000));

        // Returns a pseudorandom, uniformly distributed long value between 5000
        // (inclusive) and 10000 (exclusive)
        System.out.println(ThreadLocalRandom.current().nextDouble(5000, 10000));

    }
}
