package com.burgas.thread.priopity;

import static java.lang.System.*;
import static java.util.stream.IntStream.*;

public class TaskRange implements Runnable {

    private static final int RANGE_MINIMAL_BORDER = 0;
    private static final int RANGE_MAXIMAL_BORDER = 100;

    @Override
    public void run() {

        range(RANGE_MINIMAL_BORDER, RANGE_MAXIMAL_BORDER).forEach(value -> out.print(STR."\{value} "));
    }
}
