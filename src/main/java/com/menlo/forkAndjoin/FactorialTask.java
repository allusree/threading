package com.menlo.forkAndjoin;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class FactorialTask extends RecursiveAction {
    private static int SEQUENTIAL_THRESHOLD = 5;
    private List<BigInteger> integerList;
    private static AtomicInteger forkCount = new AtomicInteger(0);

    private FactorialTask(List<BigInteger> integerList) {
        this.integerList = integerList;
    }

    @Override
    protected void compute() {
        if (integerList.size() <= SEQUENTIAL_THRESHOLD) {
            showFactorials();
        } else {
            //splitting
            int middle = integerList.size() / 2;
            List<BigInteger> newList = integerList.subList(middle, integerList.size());
            integerList = integerList.subList(0, middle);

            FactorialTask task = new FactorialTask(newList);
            //fork() method returns immediately but spawn a new thread for the task
            task.fork();
            forkCount.getAndIncrement();
            this.compute();
        }
    }

    private void showFactorials() {
        for (BigInteger i : integerList) {
            System.out.printf("[%s] : %s! = %s, thread = %s %n",
                    LocalTime.now(), i,
                    calculateFactorial(i),
                    Thread.currentThread().getName());
            try { //sleep to simulate long running task
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static BigInteger calculateFactorial(BigInteger input) {
        BigInteger factorial = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE;
             i.compareTo(input) <= 0;
             i = i.add(BigInteger.ONE)) {
            factorial = factorial.multiply(i);
        }
        return factorial;
    }

    public static void main(String[] args) {
        List<BigInteger> list = new ArrayList<>();
        for (int i = 3; i < 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }
        ForkJoinPool.commonPool().invoke(new FactorialTask(list));
        ForkJoinPool.commonPool().invoke(new FactorialTask(list));
    }
}
