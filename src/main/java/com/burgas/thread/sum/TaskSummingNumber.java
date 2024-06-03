package com.burgas.thread.sum;

import static java.util.stream.IntStream.*;

public class TaskSummingNumber implements Runnable {

    private static final int INITIAL_VALUE_RESULT_NUMBER = 0;
    private final int fromNumber;
    private final int toNumber;
    private int resultNumber;

    public TaskSummingNumber(int fromNumber, int toNumber) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
    }

    @Override
    public void run() {

        rangeClosed(this.fromNumber, this.toNumber)
                .forEach(value -> this.resultNumber += value);
        TwoThreadsSummary.printThreadNameAndNumber(this.resultNumber);
    }

    public int getFromNumber() {
        return fromNumber;
    }

    public int getToNumber() {
        return toNumber;
    }

    public int getResultNumber() {
        return resultNumber;
    }
}
