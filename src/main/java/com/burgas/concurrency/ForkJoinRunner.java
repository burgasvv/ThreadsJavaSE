package com.burgas.concurrency;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinRunner {

    public static void main(String[] ignoredArgs) {

        try (
                ForkJoinPool forkJoinPool = new ForkJoinPool(16)
        ){
            long start = System.currentTimeMillis();
            forkJoinPool.invoke(
                    new JsonRecursiveAction(64)
            );
            long end = System.currentTimeMillis();
            System.out.println(end - start);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
