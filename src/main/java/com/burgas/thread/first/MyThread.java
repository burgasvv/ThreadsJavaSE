package com.burgas.thread.first;

import static java.lang.System.out;

public class MyThread extends Thread {

    @Override
    public void run() {

        out.println(currentThread().getName());
    }
}
