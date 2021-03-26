package task1;

import com.sun.xml.internal.txw2.IllegalAnnotationException;
import task6.Metric;

public interface Calculator {
    @Param(number = 10)
    @Metric
    int calc(int number) throws IllegalAnnotationException, IllegalArgumentException, NoSuchMethodException;
}
