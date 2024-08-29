package com.burgas.concurrency;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import static java.lang.System.*;
import static java.lang.Thread.*;

public class JsonRecursiveAction extends RecursiveAction {

    private final int value;

    public JsonRecursiveAction(int value) {
        this.value = value;
    }

    @Override
    protected void compute() {

        if (value <= 4) {
            out.println(
                    STR."\{currentThread().getName()} before: \{value}"
            );
            try {
                sleep(2000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            out.println(
                    STR."\{currentThread().getName()} after: \{value}"
            );

        } else {
            JsonRecursiveAction jsonRecursiveAction = new JsonRecursiveAction(value / 2);
            JsonRecursiveAction jsonRecursiveAction2 = new JsonRecursiveAction(value / 2);
            invokeAll(
                    List.of(jsonRecursiveAction, jsonRecursiveAction2)
            );
        }
    }
}
