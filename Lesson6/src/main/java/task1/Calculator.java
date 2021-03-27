package task1;

import task6.Metric;

public interface Calculator {
    @Param(number = 10)
    @Metric
    int calc(int number) throws IllegalArgumentException, NoSuchMethodException;
}
