package com.burgas.thread.notify;

public class ThreadWorkingMain {

    public static void main(String[] args) {

        final int brokerMaxStoredMessages = 5;
        final MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages);

        final Thread producingThread = new Thread(
                new MessageProducingTask(messageBroker)
        );

        final Thread consumingThread = new Thread(
                new MessageConsumingTask(messageBroker)
        );

        producingThread.start();
        consumingThread.start();
    }
}
