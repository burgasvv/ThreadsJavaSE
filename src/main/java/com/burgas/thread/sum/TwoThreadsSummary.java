package com.burgas.thread.sum;

import java.util.concurrent.*;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class TwoThreadsSummary {

    private static final int FROM_NUMBER_FIRST_THREAD = 1;
    private static final int TO_NUMBER_FIRST_THREAD = 500;
    private static final int FROM_NUMBER_SECOND_THREAD = 501;
    private static final int TO_NUMBER_SECOND_THREAD = 1000;
    private static final long THREAD_SLEEP_TIME_MILLIS = 3000;
    private static final String TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER = "%s : %d\n";


    public static void main(String[] args) {

        TaskSummingNumber taskSummingNumberFirst = new TaskSummingNumber(
                FROM_NUMBER_FIRST_THREAD, TO_NUMBER_FIRST_THREAD
        );
        TaskSummingNumber taskSummingNumberSecond = new TaskSummingNumber(
                FROM_NUMBER_SECOND_THREAD, TO_NUMBER_SECOND_THREAD
        );

        ThreadFactory threadFactory = Thread.ofPlatform().factory();
        Thread taskThreadFirst = threadFactory.newThread(taskSummingNumberFirst);
        Thread taskThreadSecond = threadFactory.newThread(taskSummingNumberSecond);
        taskThreadFirst.start();
        taskThreadSecond.start();

        joinThread(taskThreadFirst, taskThreadSecond);

        final int resultSumTwoTasks = taskSummingNumberFirst.getResultNumber()
                + taskSummingNumberSecond.getResultNumber();

        printThreadNameAndNumber(resultSumTwoTasks);
    }

    private static TaskSummingNumber startSubTask(final int fromNumber, final int toNumber) {

        TaskSummingNumber subTask = new TaskSummingNumber(fromNumber, toNumber);
        final Thread thread = new Thread(subTask);
        thread.start();
        try {
            thread.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return subTask;
    }

    private static void joinThread(final Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.join();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void sleepThread() {
        try {
            Thread.sleep(THREAD_SLEEP_TIME_MILLIS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printThreadNameAndNumber(final int number) {
        out.printf(TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER, currentThread().getName(), number);
    }
}
