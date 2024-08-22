package com.burgas.thread.priopity;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class PriorityThread {

    private static final String MESSAGE_TEMPLATE_THREAD_NAME = "%s : %d\n";
    private static final String MESSAGE_FINISHED_THREAD = "Main thread is finished";

    public static void main(String[] ignoredArgs) {

        Thread thread = new Thread(new TaskRange());
        thread.setPriority(MAX_PRIORITY);
        thread.start();

        out.println(MESSAGE_FINISHED_THREAD);
    }

    @SuppressWarnings("unused")
    private static void printNameAndPriorityThread(final Thread thread) {
        out.printf(MESSAGE_TEMPLATE_THREAD_NAME, thread.getName(), thread.getPriority());
    }
}
