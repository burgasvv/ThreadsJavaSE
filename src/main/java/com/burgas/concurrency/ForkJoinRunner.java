package com.burgas.concurrency;

import java.util.concurrent.ForkJoinPool;

import static java.lang.System.*;

public class ForkJoinRunner {

    private static final Integer value = 500;

    public static void main(String[] ignoredArgs) {

        try (
                ForkJoinPool forkJoinPool = new ForkJoinPool(16)
        ){
            long start = currentTimeMillis();
            forkJoinPool.invoke(
                    new JsonRecursiveAction(64)
            );
            long end = currentTimeMillis();
            out.println(end - start);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        out.println(
                STR."Value: \{value}"
        );
    }
}
