package com.burgas.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class IncrementingThread {

    private static int counter = 0;
    private static int firstCounter = 0;
    private static int secondCounter = 0;
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
    private static final int INCREMENT_AMOUNT_FIRST_COUNTER = 500;
    private static final int INCREMENT_AMOUNT_SECOND_COUNTER = 600;
    private static final Object LOCK_TO_INCREMENT_FIRST_COUNTER = new Object();
    private static final Object LOCK_TO_INCREMENT_SECOND_COUNTER = new Object();


    public static void main(String[] ignoredArgs) throws InterruptedException {

        final Counter counter1 = new Counter();
        final Counter counter2 = new Counter();

        Thread firstThread = createSynchronizedIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER);
        Thread secondThread = createSynchronizedIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER);

        startCurrentThreads(firstThread, secondThread);
        joinCurrentThreads(firstThread, secondThread);

        firstThread = createVolatileIncrementingCounterThread(INCREMENT_AMOUNT_FIRST_COUNTER);
        secondThread = createVolatileIncrementingCounterThread(INCREMENT_AMOUNT_SECOND_COUNTER);

        startCurrentThreads(firstThread, secondThread);
        joinCurrentThreads(firstThread, secondThread);

        firstThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_FIRST_COUNTER, _ -> incrementFirstCounter()
        );
        secondThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_FIRST_COUNTER, _ -> incrementFirstCounter()
        );
        Thread thirdThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_SECOND_COUNTER, _ -> incrementSecondCounter()
        );
        Thread fourthThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_SECOND_COUNTER, _ -> incrementSecondCounter()
        );

        startCurrentThreads(firstThread,secondThread,thirdThread,fourthThread);
        joinCurrentThreads(firstThread,secondThread,thirdThread,fourthThread);

        firstThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_FIRST_COUNTER, _ -> counter1.increment()
        );
        secondThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_FIRST_COUNTER, _ -> counter1.increment()
        );
        thirdThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_SECOND_COUNTER, _ -> counter2.increment()
        );
        fourthThread = createIncrementingCounterThreadWithConsumer(
                INCREMENT_AMOUNT_SECOND_COUNTER, _ -> counter2.increment()
        );

        startCurrentThreads(firstThread,secondThread,thirdThread,fourthThread);
        joinCurrentThreads(firstThread,secondThread,thirdThread,fourthThread);

        out.println(STR."Synchonized counter: \{counter}");
        out.println(STR."Volatile counter: \{ATOMIC_INTEGER}");
        out.println(STR."First counter: \{firstCounter}");
        out.println(STR."Second counter: \{secondCounter}");
        out.println(STR."Class counter first object: \{counter1.getCounter()}");
        out.println(STR."Class counter second object: \{counter2.getCounter()}");
    }

    public static Thread createSynchronizedIncrementingCounterThread(final int incrementAmount) {
        return new Thread(
                () -> range(0, incrementAmount).forEach(_ -> incrementCounter())
        );
    }

    public static Thread createVolatileIncrementingCounterThread(final int incrementAmount) {
        return new Thread(
                () -> range(0, incrementAmount).forEach(_ -> ATOMIC_INTEGER.getAndIncrement())
        );
    }

    public static Thread createIncrementingCounterThreadWithConsumer(final int incrementAmount,
                                                                     final IntConsumer incrementingOperation) {
        return new Thread(
                () -> range(0, incrementAmount).forEach(incrementingOperation)
        );
    }

    private static void startCurrentThreads(final Thread... threads) {
        stream(threads).forEach(Thread::start);
    }

    private static void joinCurrentThreads(final Thread... threads) {
        stream(threads).forEach(thread -> {
            try {
                thread.join();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static synchronized void incrementCounter() {
        counter++;
    }

    private static void incrementFirstCounter() {
        synchronized (LOCK_TO_INCREMENT_FIRST_COUNTER) {
            firstCounter++;
        }
    }

    private static void incrementSecondCounter() {
        synchronized (LOCK_TO_INCREMENT_SECOND_COUNTER) {
            secondCounter++;
        }
    }
}
