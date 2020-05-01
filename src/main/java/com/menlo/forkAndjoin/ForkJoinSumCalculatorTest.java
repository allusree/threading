package com.menlo.forkAndjoin;

public class ForkJoinSumCalculatorTest {
    public static void main(String[] args) {

        System.out.println("ForkJoin sum : " +  ForkJoinSumCalculator.forkJoinSum(10_000_000L));
    }
}
