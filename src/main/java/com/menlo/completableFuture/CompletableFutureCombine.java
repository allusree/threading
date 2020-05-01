package com.menlo.completableFuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureCombine {

    public static void main(String[] args) throws Exception {

        //Combining two dependent CompletableFutures together
        //combineTwoDependentCompletableFutures();

        //Combining two dependent CompletableFutures together
       // combineTwoInDependentCompletableFutures();

        // Combining multiple CompletableFutures together
        //combineAllof();

        // Combining multiple CompletableFutures together
        //combineAnyof();

        // Handle exceptions
           // handleExceptions();
    }




    static CompletableFuture<String> getUserName() {
        return CompletableFuture.supplyAsync(() -> {
            Random random = new Random();
            System.out.println("In getUserName : " + Thread.currentThread().getName());
            return java.util.UUID.randomUUID().toString();
        });
    }

    static CompletableFuture<Map<String,Integer>> getRating(String userName) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("In getRating : " + Thread.currentThread().getName());
            Map<String,Integer> nameMap= new HashMap<>();
             nameMap.putIfAbsent(userName, userName.length());
            return nameMap;
        });
    }

    private static void combineTwoDependentCompletableFutures() throws Exception{
      /*  CompletableFuture<CompletableFuture< Map<String,Integer>>> resultFuture = getUserName()
                .thenApply(user -> getRating(user));*/

        CompletableFuture< Map<String,Integer>> result = getUserName()
                .thenCompose(user -> getRating(user));
        /**
         * If your callback function returns a CompletableFuture,
         * and you want a flattened result from the CompletableFuture chain (which in most cases you would),
         * then use thenCompose().
         */
        System.out.println(result.get(1,TimeUnit.MINUTES));
    }

    private static void combineTwoInDependentCompletableFutures()
            throws Exception {
        CompletableFuture<Integer> maxFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("In max : " + Thread.currentThread().getName());
            return Math.max(new Random().nextInt(), new Random().nextInt());
        });
        CompletableFuture<Integer> minFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("In min : " + Thread.currentThread().getName());
            return Math.min(new Random().nextInt(), new Random().nextInt());
        });
        // combine
        CompletableFuture<Integer> combinedFuture = maxFuture
                .thenCombine(minFuture, (maxValue, minValue) -> { //thenCombine() will be called when both the Futures are complete.
                    return maxValue + minValue;
                });
        System.out.println(combinedFuture.get(1, TimeUnit.SECONDS));
    }

    private static void combineAllof() throws Exception{
        List<Integer> list = IntStream.rangeClosed(0, 100).boxed().collect(Collectors.toList());
        List<CompletableFuture<Integer>> futureList = list.stream().map(id -> { //A list of 100 elements
            return CompletableFuture.supplyAsync(() -> { // Thread
                System.out.println("In multiply : " + Thread.currentThread().getName());
                /*if(id.equals(5)){
                    throw new IllegalArgumentException("id is 5");
                }*/
                return id * id; //multiply element
            }).handle((res, ex) -> {
                if(ex != null) {
                    System.out.println("Oops! We have an exception - " + ex.getMessage());
                    return null;
                }
                return res;
            });
        }).collect(Collectors.toList());

        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

        //problem is returns CompletableFuture<Void>
        //now how to get the result
        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -

        CompletableFuture<List<Integer>> allElementFuture = allFutures.thenApply(v -> {
            return futureList.stream().filter(Objects::nonNull)
                    .map(multiFuture -> multiFuture.join())
                    .collect(Collectors.toList());

        });// calling future.join() when all the futures are complete,no blocking anywhere
        //join() method is similar to get(). The only difference is that it throws an unchecked exception if the underlying CompletableFuture completes exceptionally.

        List<Integer> integersList = allElementFuture.get(1, TimeUnit.MINUTES);
        integersList.forEach(System.out::println);

    }
    private static void combineAnyof() throws Exception {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("In future1 : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("In future2 : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("In future3 : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of Future 3";
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);

        System.out.println(anyOfFuture.get()); // Result of Future 2

        /**
         * takes vararg
         *
         *  problem with CompletableFuture.anyOf() is that if
         *  CompletableFutures that return results of different types,
         *  then we won’t know the type of your final CompletableFuture.
         */
    }

    private static void handleExceptions() throws Exception {
        {
            List<Integer> list = IntStream.rangeClosed(0, 100).boxed().collect(Collectors.toList());
            List<CompletableFuture<Integer>> futureList = list.stream().map(id -> { //A list of 100 elements
                return CompletableFuture.supplyAsync(() -> { // Thread
                    System.out.println("In multiply : " + Thread.currentThread().getName());
                    if (id.equals(5)) {
                        throw new IllegalArgumentException("id is 5");
                    }
                    return id * id; //multiply element
                })
                        .exceptionally(e -> {
                            System.out.println("Oops! We have an exception - " + e.getMessage());
                            return null;
                        });

                   /*     .handle((res, ex) -> {
                    if(ex != null) {
                        System.out.println("Oops! We have an exception - " + ex.getMessage());
                        return null;
                    }
                    return res;
                });*/
            }).collect(Collectors.toList());

            // Create a combined Future using allOf()
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

            //problem is returns CompletableFuture<Void>
            //now how to get the result
            // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -

            CompletableFuture<List<Integer>> allElementFuture = allFutures.thenApply(v -> {
                return futureList.stream().filter(Objects::nonNull)
                        .map(multiFuture -> multiFuture.join())
                        .collect(Collectors.toList());

            });// calling future.join() when all the futures are complete,no blocking anywhere
            //join() method is similar to get(). The only difference is that it throws an unchecked exception if the underlying CompletableFuture completes exceptionally.

            List<Integer> integersList = allElementFuture.get(1, TimeUnit.MINUTES);
            integersList.forEach(System.out::println);

        }


    }

}
