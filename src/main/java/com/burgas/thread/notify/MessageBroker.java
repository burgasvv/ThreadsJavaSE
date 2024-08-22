package com.burgas.thread.notify;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.Thread.currentThread;

public class MessageBroker {

    private final Queue<Message> messagesToBeConsumed;
    private final int maxStoredMessages;

    public MessageBroker(int maxStoredMessages) {
        this.maxStoredMessages = maxStoredMessages;
        messagesToBeConsumed = new ArrayDeque<>(maxStoredMessages);
    }

    @SuppressWarnings("unused")
    public Queue<Message> getMessagesToBeConsumed() {
        return messagesToBeConsumed;
    }

    @SuppressWarnings("unused")
    public int getMaxStoredMessages() {
        return maxStoredMessages;
    }

    public synchronized void produce(final Message message) {

        try {
            while (this.messagesToBeConsumed.size() >= this.maxStoredMessages)
                super.wait();

            messagesToBeConsumed.add(message);
            this.notify();

        } catch (InterruptedException e) {
            currentThread().interrupt();
        }

    }

    public synchronized Message consume() {

        try {
            while (this.messagesToBeConsumed.isEmpty()) {
                super.wait();
            }

            final Message consumedMessage = messagesToBeConsumed.poll();
            super.notify();

            return consumedMessage;

        } catch (InterruptedException e) {

            currentThread().interrupt();
            throw new RuntimeException(e);
        }

    }
}
