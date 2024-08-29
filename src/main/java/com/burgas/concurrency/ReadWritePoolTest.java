package com.burgas.concurrency;

import com.sun.tools.attach.AttachNotSupportedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class ReadWritePoolTest {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final List<String> list = new ArrayList<>();
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();

    public static void main(String[] ignoredArgs)

            throws ExecutionException, InterruptedException,
            IOException, AttachNotSupportedException
    {

        executorService.submit(
                Executors.callable(
                        () -> {
                            threadLocal.set(
                                    new ArrayList<>(
                                            List.of("Slava", "Boris", "Danil", "Ruslan", "Kiril", "Nikita")
                                    )
                            );

                            threadLocal.get().forEach(
                                    s -> {
                                        list.add(s);
                                        out.println(
                                                STR."\{Thread.currentThread().getName()} :: add/write : \{s}"
                                        );
                                        counter.getAndIncrement();
                                    }
                            );
                        }
                )
        ).get();

        executorService.submit(
                Executors.callable(
                        () -> {
                            list.forEach(
                                    s ->
                                            out.println(
                                                    STR."\{Thread.currentThread().getName()} :: read : \{s}"
                                            )
                            );
                            counter.getAndIncrement();
                        }
                )
        ).get();

        out.println(
                counter.get()
        );
        out.println(list);
        executorService.shutdown();
    }

}
