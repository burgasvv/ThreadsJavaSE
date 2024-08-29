package com.burgas.concurrency;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class ExecutorsRunner {

    private static int count = 0;

    public static void main(String[] ignoredArgs) throws IOException {

        Callable<Object> callable = Executors.callable(
                new JsonRunnableTask()
        );

        try (
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
                ExecutorService cachedThreadPool = Executors.newCachedThreadPool()
        ) {

            Future<Object> jsonSubmit = fixedThreadPool.submit(callable);
            Future<?> messageSubmit = fixedThreadPool.submit(
                    () -> {
                        try {
                            for (int i = 0; i < 10; i++) {
                                sleep(2000);
                                //noinspection StringTemplateMigration
                                out.println(
                                        currentThread().getName().toUpperCase() +
                                        ": I'm submitted " + count++
                                );
                            }

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );


            jsonSubmit.get();
            messageSubmit.get();


            Future<?> cachedSubmitMessage = cachedThreadPool.submit(
                    () -> {
                        try {
                            for (int i = 0; i < 6; i++) {
                                sleep(2000);
                                //noinspection StringTemplateMigration
                                out.println(
                                        currentThread()
                                                .getName().toUpperCase() +
                                        ": Cached thread pool submitted " + count++
                                );
                            }

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            Future<?> cachedSubmit = cachedThreadPool.submit(
                    () -> {
                        try {
                            sleep(1000);
                            //noinspection StringTemplateMigration
                            out.println(
                                    currentThread()
                                            .getName().toUpperCase() +
                                    ": Cached thread pool submitted new Message"
                            );

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );


            cachedSubmitMessage.get();
            cachedSubmit.get();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
