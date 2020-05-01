package com.menlo.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public class ReentrantReadWriteLockDemo {

    private static Map<String, String> syncHashMap = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void put(String key, String value) throws InterruptedException {

        try {
            writeLock.lock();
            System.out.println("writting  Locks " + Thread.currentThread().getName() );
            syncHashMap.put(key, value);
            sleep(1000);
        } finally {
            System.out.println("Un Locks" + Thread.currentThread().getName() );
            writeLock.unlock();
        }

    }

    public String get(String key) {
        try {
            readLock.lock();
            System.out.println("reading lock " + Thread.currentThread().getName()  );
            return syncHashMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public String remove(String key) {
        try {
            writeLock.lock();
            return syncHashMap.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncHashMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    boolean isReadLockAvailable() {
        return readLock.tryLock();
    }

    public static void main(String[] args) throws InterruptedException {

        final int threadCount = 3;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        ReentrantReadWriteLockDemo object = new ReentrantReadWriteLockDemo();


        Runnable writer = () -> {
            IntStream.rangeClosed(0, 10).forEach(i -> {
                try {
                    object.put("key" + i, "value" + i);
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        };
        Runnable writer2 = () -> {
            IntStream.rangeClosed(0, 3).forEach(i -> {
                try {
                    object.put("key" + i, "value" + i);
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        };

        Runnable reader1 = () -> {
            for (int i = 0; i < 3; i++) {
                object.get("key" + i);
            }
        };
        Runnable reader2 = () -> {
            IntStream.rangeClosed(0, 3).forEach(i -> {
                object.get("key" + i);
            });

        };
        service.execute(writer);
        service.execute(writer2);
        service.execute(reader2);

        service.shutdown();
    }

}
