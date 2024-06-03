package com.burgas.different.locks;

import org.jetbrains.annotations.Contract;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The type Counter guarded by read write lock.
 */
public final class CounterGuardedByReadWriteLock extends AbstractCounter{

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = this.readWriteLock.readLock();

    private final Lock writeLock = this.readWriteLock.writeLock();

    @Contract(pure = true)
    @Override
    public Lock getReadLock() {
        return this.readLock;
    }

    @Contract(pure = true)
    @Override
    public Lock getWriteLock() {
        return this.writeLock;
    }
}