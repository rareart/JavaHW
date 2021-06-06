package factorialTest;

import multithreading.Calc;
import multithreading.FactorialCalcException;
import org.junit.Test;

public class FactorialCalc {
    @Test
    public void CalcTestForFile1() throws FactorialCalcException {
        new Calc().calcFactorial("random1.txt", true);
    }

    @Test
    public void CalcTestForFile2() throws FactorialCalcException {
        new Calc().calcFactorial("random2.txt", true);
    }

    @Test
    public void CalcTestForFile3() throws FactorialCalcException {
        new Calc().calcFactorial("random3.txt", true);
    }
}
