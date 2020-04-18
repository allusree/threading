package com.menlo.concurrent.pojo;

import java.util.concurrent.*;

public class Student implements Callable<Status> {
    private String name;
    private CyclicBarrier schoolBarier;

    public Student(String name, CyclicBarrier schoolBarier) {
        this.name = name;
        this.schoolBarier = schoolBarier;
    }

    @Override
    public Status call() throws Exception {
        System.out.println(name + " is waiting at the school door  " + this.schoolBarier.getNumberWaiting() + " others");
        try {
            this.schoolBarier.await(50, TimeUnit.MILLISECONDS);
            System.out.println(name + " can enter to school");
        } catch (InterruptedException | BrokenBarrierException e ) {
            System.err.println(name + " abnormally left the school because of interruption, failure, or timeout. school barier is broken.");
            return new Status(name, "walking away from the school");
        } catch (TimeoutException e) {
            System.err.println(name + " waited too long and is going elsewhere . Others that are waiting are leaving");
            return new Status(name, "walking away from the school");
        }
        return new Status(name, "school start");
    }


}
