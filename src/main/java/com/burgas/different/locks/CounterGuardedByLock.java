package com.burgas.different.locks;

import org.jetbrains.annotations.Contract;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Counter guarded by lock.
 */
public final class CounterGuardedByLock extends AbstractCounter{

    private final Lock lock = new ReentrantLock();

    @Contract(pure = true)
    @Override
    public Lock getReadLock() {
        return this.lock;
    }

    @Contract(pure = true)
    @Override
    public Lock getWriteLock() {
        return this.lock;
    }
}