package com.burgas.thread.interrupted;

import org.jetbrains.annotations.NotNull;

import static java.lang.System.*;
import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.*;

public class InterruptedThread {

    private static final String MESSAGE_REQUEST_WAS_SENT = "Request was sent";
    private static final int DURATION_IN_SECONDS_DELIVERING_RESPONSE = 1;
    private static final String MESSAGE_RESPONSE_WAS_RECEIVED = "Response was received";
    private static final String MESSAGE_SERVER_WAS_STOPPED = "Server was stopped";
    private static final String MESSAGE_THREAD_WAS_INTERRUPTED = "Thread was interrupted";


    public static void main(String[] args) throws InterruptedException {

        final Thread communicatingThread = getCommunicatingThread();
        communicatingThread.start();

        SECONDS.sleep(5);

        final Thread stoppingServer = getStoppingServerThread(communicatingThread);
        stoppingServer.start();
    }

    @NotNull
    private static Thread getStoppingServerThread(Thread communicatingThread) {
        return new Thread(
                () -> {

                    if (isServerStopped()) {
                        communicatingThread.interrupt();
                        sendMessageServerWasStopped();
                    }
                }
        );
    }

    @NotNull
    private static Thread getCommunicatingThread() {
        return new Thread(
                () -> {

                    while (!currentThread().isInterrupted()) {

                        try {
                            doRequest();

                        } catch (InterruptedException e) {
                            currentThread().interrupt();
                            out.println(MESSAGE_THREAD_WAS_INTERRUPTED);
                        }

                    }
                }
        );
    }

    private static void doRequest() throws InterruptedException {

        out.println(MESSAGE_REQUEST_WAS_SENT);
        SECONDS.sleep(DURATION_IN_SECONDS_DELIVERING_RESPONSE);
        out.println(MESSAGE_RESPONSE_WAS_RECEIVED);
    }

    private static boolean isServerStopped() {
        return true;
    }

    private static void sendMessageServerWasStopped() {
        out.println(MESSAGE_SERVER_WAS_STOPPED);
    }
}
