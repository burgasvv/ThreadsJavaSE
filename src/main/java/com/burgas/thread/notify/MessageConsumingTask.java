package com.burgas.thread.notify;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public record MessageConsumingTask(MessageBroker messageBroker) implements Runnable {

    private static final int MESSAGE_DURATION_TO_SLEEP_BEFORE_CONSUMING = 3;
    private static final String TEMPLATE_MESSAGE_OF_MESSAGE_IS_CONSUMED = "Message \"%s\" is consumed\n";

    @Override
    public void run() {

        try {
            while (!currentThread().isInterrupted()) {

                SECONDS.sleep(MESSAGE_DURATION_TO_SLEEP_BEFORE_CONSUMING);
                final Message consumeMessage = this.messageBroker.consume();
                out.printf(TEMPLATE_MESSAGE_OF_MESSAGE_IS_CONSUMED, consumeMessage);
            }

        } catch (InterruptedException interruptedException) {

            currentThread().interrupt();
        }
    }
}
