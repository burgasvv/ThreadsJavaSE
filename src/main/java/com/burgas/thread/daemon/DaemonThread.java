package com.burgas.thread.daemon;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;


public class DaemonThread {

    private static final String MESSAGE_MAIN_THREAD_IS_FINISHED = "Main thread is finished";
    private static final String MESSAGE_DAEMON_THREAD_IS_FINISHED = "Daemon thread is finished";
    private static final String MESSAGE_TEMPLATE_THREAD_NAME_AND_DAEMON_STATUS = "%s : %b\n";
    private static final int MAIN_THREAD_SECONDS_SLEEP = 10;

    public static void main(String[] ignoredArgs) throws InterruptedException {

        printThreadNameAndDaemonStatus(currentThread());

        final Thread thread = new Thread(
                new TaskWorking()
        );
        thread.setDaemon(true);
        printThreadNameAndDaemonStatus(thread);
        thread.start();

        SECONDS.sleep(MAIN_THREAD_SECONDS_SLEEP);

        out.println(MESSAGE_MAIN_THREAD_IS_FINISHED);
        out.println(MESSAGE_DAEMON_THREAD_IS_FINISHED);
    }

    private static void printThreadNameAndDaemonStatus(final Thread thread) {

        out.printf(MESSAGE_TEMPLATE_THREAD_NAME_AND_DAEMON_STATUS, thread.getName(), thread.isDaemon());
    }
}
