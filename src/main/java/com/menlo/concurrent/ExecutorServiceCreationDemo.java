package com.menlo.concurrent;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceCreationDemo {
    ExecutorService threadPoolExecutorExecutorService =
            new ThreadPoolExecutor(1, 1, 1L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
    ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();

    ExecutorService fixedSizeExecutorService = Executors.newFixedThreadPool(10);

    ExecutorService pooledExecutorService = Executors.newScheduledThreadPool(10);

    //Threads that have not been used for sixty seconds are terminated and removed from the cache
    ExecutorService cachedExecutorService = Executors.newCachedThreadPool();

    //every thread is backed with it's own deque. When one thread is done with it's tasks it takes task from other thread's deque and executes it.
    //Mostly used in ForkJoinPool
    ExecutorService workStealExecutorService = Executors.newWorkStealingPool(10); // Java 8,


}
