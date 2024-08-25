package com.burgas.concurrency;

import org.intellij.lang.annotations.Language;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class JsonRunnableTask implements Runnable {

    @Override
    public void run() {

        @Language("JSON") String json = """
                        {
                          "firstname": "Slava",
                          "lastname": "Burgas",
                          "email": "burgassme@gmail.com"
                        }""";

        try {
            sleep(5000);
            //noinspection StringTemplateMigration
            out.println(
                    currentThread()
                            .getName().toUpperCase() + ": " + json
            );

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
