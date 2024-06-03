package com.burgas.thread.first;

import java.util.stream.IntStream;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class CurrentThread {

    private final static int CREATED_THREADS_AMOUNT = 5;

    public static void main(String[] args) {

        out.println(currentThread().getName());

        final MyThread myThread = new MyThread();
        myThread.start();

        final Runnable taskGetThreadName = () -> out.println(currentThread().getName());
        final Thread thread = new Thread(taskGetThreadName);
        thread.start();

        final Runnable taskCreateThreads = () ->
            IntStream.range(0, CREATED_THREADS_AMOUNT)
                    .forEach(_ -> startThread(taskGetThreadName));
        startThread(taskCreateThreads);
    }

    private static void startThread(final Runnable runnable) {

        final Thread thread = new Thread(runnable);
        thread.start();
    }
}
