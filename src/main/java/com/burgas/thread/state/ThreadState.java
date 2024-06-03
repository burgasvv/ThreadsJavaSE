package com.burgas.thread.state;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class ThreadState {

    private static final String MESSAGE_TEMPLATE_THREAD_STATE = "%s : %s\n";

    public static void main(String[] args) {

        final Thread mainThread = currentThread();
        getThreadState(mainThread);

        final Thread thread = new Thread(
                () -> {
                    try {
                        mainThread.join();
                        sleep(1000);
                        getThreadState(currentThread());

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        getThreadState(thread);
        thread.start();

        try {
            sleep(1000);
            getThreadState(thread);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getThreadState(final Thread thread) {

        out.printf(MESSAGE_TEMPLATE_THREAD_STATE, thread.getName(), thread.getState());
    }
}
