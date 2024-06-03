package com.burgas.uncaught.exception;

import com.burgas.thread.factory.DaemonThreadWithUncaughtException;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

import static java.lang.System.out;

public class UncaughtException {

    private static final String MESSAGE_TEMPLATE_EXCEPTION =
            "Exception was thrown with message \"%s\" in thread - %s\n";

    public static void main(String[] args) throws InterruptedException {

        final UncaughtExceptionHandler uncaughtExceptionHandler = (thread, exception) ->
                out.printf(MESSAGE_TEMPLATE_EXCEPTION, exception.getMessage(), thread.getName());

        final ThreadFactory threadFactory = new DaemonThreadWithUncaughtException(
                uncaughtExceptionHandler
        );


        final Thread firstThread = threadFactory.newThread(
                new TaskException()
        );
        firstThread.start();

        final Thread secondThread = threadFactory.newThread(
                new TaskException()
        );
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
}
