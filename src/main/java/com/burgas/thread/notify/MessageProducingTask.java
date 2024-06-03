package com.burgas.thread.notify;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MessageProducingTask implements Runnable {

    private static final String MESSAGE_OF_MESSAGE_IS_PRODUCED = "Message \"%s\" is produced\n";
    private static final int MESSAGE_DURATION_TO_SLEEP_BEFORE_PRODUCING = 1;
    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;

    public MessageProducingTask(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
        messageFactory = new MessageFactory();
    }

    public MessageBroker getMessageBroker() {
        return messageBroker;
    }

    @Override
    public void run() {

        try {
            while (!currentThread().isInterrupted()) {

                final Message producedMessage = this.messageFactory.create();
                SECONDS.sleep(MESSAGE_DURATION_TO_SLEEP_BEFORE_PRODUCING);
                this.messageBroker.produce(producedMessage);
                out.printf(MESSAGE_OF_MESSAGE_IS_PRODUCED, producedMessage);
            }

        } catch (InterruptedException e) {

            currentThread().interrupt();
        }
    }


    private static final class MessageFactory {

        private static final int INITIAL_NEXT_MESSAGE_INDEX = 1;
        private static final String TEMPLATE_CREATED_MESSAGE_INDEX = "Message#%d";
        private int nextMessageIndex;

        public MessageFactory() {
            nextMessageIndex = INITIAL_NEXT_MESSAGE_INDEX;
        }

        public int getNextMessageIndex() {
            return nextMessageIndex;
        }

        public Message create() {

            return new Message(format(TEMPLATE_CREATED_MESSAGE_INDEX, nextMessageIndex++));
        }
    }
}
