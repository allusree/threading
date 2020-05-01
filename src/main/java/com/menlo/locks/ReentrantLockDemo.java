package com.menlo.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private int count = 0;
    private Lock lock = new ReentrantLock();

    public int increment() {
        synchronized (this) {
            return ++count;
        }
    }

    public int inc() {
        int newCount = 0;
        try {
            lock.lock();
            // access to the shared resource
            newCount = ++count;
        } finally {
            lock.unlock();

        }
        return newCount;
    }

    public void performTryLock() throws Exception {
        //will wait for one second and will give up waiting if the lock isn't available.
        boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
        if(isLockAcquired) {
            try {
               int newCount = ++count;
            } finally {
                lock.unlock();
            }
        }
    }
}
