package com.menlo.forkAndjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;


public class ForkJoinSumCalculator extends RecursiveTask<Long> { //RecursiveTask which extends ForkJoinTask
    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();//initializes the pool with default settings
    public static final ForkJoinPool FORK_JOIN_POOL1 = new ForkJoinPool(4);//With parallelism level i.e. how many cores to be used
    public static final long THRESHOLD = 10_000;
    private static AtomicInteger forkCount = new AtomicInteger(0);
    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        System.out.println("In compute()");
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        System.out.println("start : "+ start + " , end : " + start + length / 2);
        leftTask.fork();
        forkCount.getAndIncrement();
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        System.out.println("start : "+ start + length / 2 + " , end : " + end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(Long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);

        Long invoke = FORK_JOIN_POOL.invoke(task);
        System.out.println("Total Forkings" + forkCount.get());
        return invoke;
    }
}