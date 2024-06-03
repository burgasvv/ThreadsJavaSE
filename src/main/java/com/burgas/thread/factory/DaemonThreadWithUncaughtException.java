package com.burgas.thread.factory;

import org.jetbrains.annotations.NotNull;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

public record DaemonThreadWithUncaughtException(
        UncaughtExceptionHandler uncaughtExceptionHandler) implements ThreadFactory {

    @Override
    public Thread newThread(@NotNull final Runnable runnable) {

        final Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        thread.setDaemon(true);

        return thread;
    }
}
