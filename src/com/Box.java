package com;

import java.util.concurrent.locks.ReentrantLock;

public class Box {
    private volatile int cnt = 0;

    public synchronized void put(int amount, ReentrantLock lock) throws IllegalAccessException {
        lock.lock();
        if (cnt + amount > 100) throw new IllegalAccessException();
        else cnt += amount;
    }

    public int getCnt() {
        return cnt;
    }
}
