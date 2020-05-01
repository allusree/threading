package com.menlo.blocking.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        Runnable producer = () -> {
            IntStream.range(0, 20).forEach(item -> {
                        try {
                            System.out.println("[Producer] Put : " + item);
                            queue.put(item);
                            System.out.println("[Producer] Queue remainingCapacity : " + queue.remainingCapacity());
                            Thread.sleep(100);
                        } catch (Exception e) {

                        }
                    }
            );
        };

        Runnable consumer   = () -> {
            try {
                while (true) {
                    Integer take = queue.take();
                    System.out.println("[Consumer] Take : " + take);
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
