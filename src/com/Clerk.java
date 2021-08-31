package com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Clerk implements Callable<Map<String, Integer>> {
    private final Box box;
    private ReentrantLock lock;

    public Clerk(Box box, ReentrantLock lock) {
        this.box = box;
        this.lock = lock;
    }


    @Override
    public Map<String, Integer> call() throws Exception {
        int amt;
        int count = 0;
        while (box.getCnt() < 100) {
            amt = ThreadLocalRandom.current().nextInt(0, 3);
            try {
                box.put(amt, lock);
                count += amt;
            } catch (IllegalAccessException e) {
                System.err.println("Коробка заполнена!");
            } finally {
                lock.unlock();
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put(Thread.currentThread().getName(), count);
        return map;
    }
}
