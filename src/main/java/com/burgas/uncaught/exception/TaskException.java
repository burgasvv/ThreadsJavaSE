package com.burgas.uncaught.exception;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

public class TaskException implements Runnable {

    private static final String EXCEPTION_MESSAGE = "I'm exception";

    @Override
    public void run() {
        out.println(currentThread());
        throw new RuntimeException(EXCEPTION_MESSAGE);
    }
}
