package com.burgas.different.locks;

import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;
import java.util.concurrent.locks.Lock;

/**
 * The type Abstract counter.
 */
public abstract class AbstractCounter {

    private long value;

    /**
     * Get value optional long.
     *
     * @return the optional long
     */
    public final @NotNull OptionalLong getValue(){
        final Lock lock = this.getReadLock();
        lock.lock();
        try {
            return OptionalLong.of(this.value);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Increment optional long.
     *
     * @return the optional long
     */
    public final @NotNull OptionalLong increment(){
        final Lock lock = this.getWriteLock();
        lock.lock();
        try {
            return OptionalLong.of(this.value++);
        }finally {
            lock.unlock();
        }
    }

    /**
     * Gets read lock.
     *
     * @return the read lock
     */
    public abstract Lock getReadLock();

    /**
     * Gets write lock.
     *
     * @return the write lock
     */
    public abstract Lock getWriteLock();
}