package task1test;

import org.junit.Test;
import task1.CalculatorImpl;

import static org.junit.Assert.assertEquals;

public class CalculatorImplTest {
    @Test
    public void calcTest() throws IllegalArgumentException, NoSuchMethodException {
        CalculatorImpl calculator = new  CalculatorImpl();
        assertEquals(3628800, calculator.calc(10));
    }
}
