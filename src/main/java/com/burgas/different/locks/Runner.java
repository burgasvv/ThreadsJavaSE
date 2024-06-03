package com.burgas.different.locks;

import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.StringTemplate.STR;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.stream;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;

/**
 * The type Runner class for testing guarded ny lock and guarded by read write lock.
 */
public final class Runner {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args)
            throws InterruptedException {
        testCounter(CounterGuardedByLock::new);
        testCounter(CounterGuardedByReadWriteLock::new);
    }

    private static void testCounter(final Supplier<? extends AbstractCounter>counterFactory)
            throws InterruptedException {

        final AbstractCounter counter = counterFactory.get();
        final ReadingValueTask[]readingValueTasks = createReadingTasks(counter);
        final Thread[]readingValueThreads = mapToThread(readingValueTasks);

        final Runnable incrementingCounterTask = createIncrementingCounterTask(counter);
        final Thread[]incrementingCounterThreads = createThreads(
                incrementingCounterTask
        );

        startThreads(readingValueThreads);
        startThreads(incrementingCounterThreads);

        SECONDS.sleep(5);

        interruptThreads(readingValueThreads);
        interruptThreads(incrementingCounterThreads);

        waitUntilFinish(readingValueThreads);

        final Long totalAmountOfReads = findTotalAmountOfReads(readingValueTasks);
        out.println(STR."Amount of readings value: \{totalAmountOfReads}");
    }

    private static ReadingValueTask[]createReadingTasks(final AbstractCounter counter){
        return range(0, 50)
                .mapToObj(_ -> new ReadingValueTask(counter))
                .toArray(ReadingValueTask[]::new);
    }

    private static Thread[]mapToThread(final Runnable[]tasks){
        return stream(tasks).map(Thread::new).toArray(Thread[]::new);
    }

    private static void incrementCounter(final AbstractCounter counter){
        try {
            @NotNull OptionalLong along = counter.increment();
            SECONDS.sleep(1);
        } catch (final InterruptedException interruptedException) {
            currentThread().interrupt();
        }
    }

    private static Runnable createIncrementingCounterTask(final AbstractCounter counter){
        return () -> {
            while (!currentThread().isInterrupted())
                incrementCounter(counter);
        };
    }

    private static Thread[]createThreads(final Runnable task){
        return range(0, 2)
                .mapToObj(_ -> new Thread(task))
                .toArray(Thread[]::new);
    }

    private static void startThreads(final Thread[]threads){
        forEach(threads,Thread::start);
    }

    private static void forEach(final Thread[]threads, final Consumer<Thread>action){
        stream(threads).forEach(action);
    }

    private static void interruptThreads(final Thread[]threads){
        forEach(threads,Thread::interrupt);
    }

    private static void waitUntilFinish(final Thread thread){
        try {
            thread.join();
        } catch (final InterruptedException interruptedException) {
            thread.interrupt();
        }
    }

    private static void waitUntilFinish(final Thread[]threads){
        forEach(threads,Runner::waitUntilFinish);
    }

    private static Long findTotalAmountOfReads(final ReadingValueTask[]tasks){
        return stream(tasks).mapToLong(ReadingValueTask::getAmountOfReads).sum();
    }

    /**
     * The type Reading value task.
     */
    public static final class ReadingValueTask implements Runnable{

        private final AbstractCounter counter;

        private long amountOfReads;

        /**
         * Instantiates a new Reading value task.
         *
         * @param counter the counter
         */
        public ReadingValueTask(final AbstractCounter counter) {
            this.counter = counter;
        }

        /**
         * Gets amount of reads.
         *
         * @return the amount of reads
         */
        public long getAmountOfReads() {
            return amountOfReads;
        }

        @Override
        public void run() {
            while (!currentThread().isInterrupted()){
                OptionalLong aLong = this.counter.getValue();
                this.amountOfReads++;
            }
        }
    }
}