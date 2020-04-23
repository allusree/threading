package com.menlo.concurrent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceDemo {

    /**
     * {@link Runnable execute}
     *
     * @throws Exception
     */
    public void threadExecute() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnable =  () -> {
            IntStream.range(0, 10).forEach(id -> System.out.println(" id :" + id));
        };
        executorService.execute(runnable);

        executorService.execute(() -> {
            try {
                System.out.println("In Sleep");
                //Thread.sleep(2000);
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //IntStream.range(0, 10).forEach(id -> System.out.println( " id :" + id));
        });

        //Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted
        executorService.shutdown();
        //Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        //Attempts to stop all actively executing tasks, halts the processing of waiting tasks
        executorService.shutdownNow();
    }

    /**
     * {@link Runnable submit}
     *
     * @throws Exception
     */
    public void threadSubmit() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
       Callable callable =  () -> {
            System.out.println("max thread " + Thread.currentThread());
            return Math.max(1, 2);
        };
        Future<Integer> maxFuture = executorService.submit(callable);
        Future<Integer> minFuture = executorService.submit(() -> {
            System.out.println("min thread " + Thread.currentThread());
            return Math.min(1, 2);
        });

        Runnable runnableTask = () -> {
            try {
                System.out.println("Timer thread " + Thread.currentThread());
                System.out.println("Current time :: " + LocalDateTime.now());
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Future<String> timerFuture = executorService.submit(runnableTask, "DONE");
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("minimum future result : " + minFuture.get());
        System.out.println("timer future result : " + timerFuture.get());
        System.out.println("maximum future result : " + maxFuture.get());
        System.out.println("timer future result : " + timerFuture.get(1, TimeUnit.SECONDS));
    }

    /**
     * {@link Callable submit}
     *
     * @throws Exception
     */
    public void threadSubmitCallable() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Callable<String> callableTask = () -> {
            try {
                System.out.println("Timer " + Thread.currentThread());
                System.out.println("Current time :: " + LocalDateTime.now());
                TimeUnit.MILLISECONDS.sleep(10000);
                return "after sleep time :: " + LocalDateTime.now();
            } catch (Exception e) {
                throw e;
            }
        };
        Future<String> timerFuture = executorService.submit(callableTask);
        executorService.shutdown();

        System.out.println("timer future result : " + timerFuture.get());
    }

    /**
     * @throws Exception
     */
    public void threadCallableInvokeAll() throws Exception {
        Callable<String> callableTask = () -> {
            System.out.println("Timer " + Thread.currentThread().getName());
            //  TimeUnit.MILLISECONDS.sleep(50);
            return "Task's execution";
        };


        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = executorService.invokeAll(callableTasks);
        executorService.shutdownNow();
        futures.forEach(future -> {
            try {

                System.out.println("is done : " + future.isDone() + ",  is canceled : " +future.isCancelled());
                System.out.println("result : " + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @throws Exception
     */
    public void threadCallableInvokeAny() throws Exception {
        Callable<String> callableTask = () -> {
            System.out.println("Timer " + Thread.currentThread().getName());
            //  TimeUnit.MILLISECONDS.sleep(50);
            return "Task's execution";
        };


        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        String invokeAnyResult = executorService.invokeAny(callableTasks);
        executorService.shutdownNow();
        System.out.println("invokeAnyResult : " + invokeAnyResult);
    }

    /**
     * @throws Exception
     */

    public void threadSubmitCallableCancel() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Callable callableTask = () -> {
            try {
                System.out.println("Timer " + Thread.currentThread());
                System.out.println("Current time :: " + LocalDateTime.now());
               // TimeUnit.SECONDS.sleep(2);
                return "CANCELED TASK";
            } catch (Exception e) {
                throw e;
            }
        };
        Future<String> timerFuture = executorService.submit(callableTask);
        //timerFuture.cancel(true);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        if (!timerFuture.isCancelled()) {
            System.out.println("timer future result : " + timerFuture.get(1, TimeUnit.MINUTES));
        } else {
            System.out.println("timer future is cancelled");
        }
    }
}
