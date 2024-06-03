package com.burgas.thread.daemon;

import static java.lang.System.*;
import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.*;

public class TaskWorking implements Runnable{

    private static final String MESSAGE = "I'm working";
    private static final int DURATION_BETWEEN_MESSAGES = 2;


    @Override
    public void run() {

        while (!currentThread().isInterrupted()) {

            out.println(MESSAGE);
            try {
                SECONDS.sleep(DURATION_BETWEEN_MESSAGES);

            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }
}
