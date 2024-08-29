package com.burgas.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentArraylist {

    private static final List<Integer>integers = new CopyOnWriteArrayList<>();

    public static void main(String[] ignoredArgs) throws InterruptedException {

        Thread thread = new Thread(
                () -> {
                    try {
                        Thread.sleep(1000);
                        List<Integer> integerList = new ArrayList<>(
                                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                        );
                        integers.addAll(integerList);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Thread thread1 = new Thread(
                () -> {
                    try {
                        Thread.sleep(1000);
                        List<Integer> integerList = new ArrayList<>(
                                List.of(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                        );
                        integers.addAll(integerList);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

        System.out.println(integers);
    }
}
