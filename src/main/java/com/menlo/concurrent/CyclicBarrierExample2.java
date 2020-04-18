package com.menlo.concurrent;

import com.menlo.concurrent.pojo.Student;
import com.menlo.concurrent.pojo.Status;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class CyclicBarrierExample2 {
    public static void main(String[] args) {
        Integer minStudents = 4;
        Runnable runnable = () -> {
            System.out.println("Yay!!! The school is open and the parties can enter the school");
        };
        CyclicBarrier barrier = new CyclicBarrier(3, runnable);
        ExecutorService bar = Executors.newCachedThreadPool();
        Student d1 = new Student("A", barrier);
        Student d2 = new Student("B", barrier);
        Student d3 = new Student("C", barrier);
        Student d4 = new Student("D", barrier);
        Student d5 = new Student("E", barrier);
        Student d6 = new Student("F", barrier);

        List<Future<Status>> futureStudents = new LinkedList<Future<Status>>();

        // convert callable Student into future Student
        futureStudents.add(bar.submit(d1));
        futureStudents.add(bar.submit(d2));
        futureStudents.add(bar.submit(d3));
        futureStudents.add(bar.submit(d4));
        futureStudents.add(bar.submit(d5));
        futureStudents.add(bar.submit(d6));

        // loop through the Student and wait for them to all be done
        for (Future<Status> futureStudentStatus : futureStudents) {
            try {
                System.out.println(futureStudentStatus.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}