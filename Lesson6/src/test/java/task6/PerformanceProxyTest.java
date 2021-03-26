package task6;

import org.junit.Test;
import task1.Calculator;
import task1.CalculatorImpl;

public class PerformanceProxyTest {

    @Test
    public void performanceProxyTest(){
        Calculator calculator = new CalculatorImpl();
        try {
            System.out.println(calculator.calc(10));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("------------------");
        PerformanceProxy<Calculator> calculatorPerformanceProxy = new PerformanceProxy<>(calculator);
        Calculator calcWithPP = calculatorPerformanceProxy.getPerformanceProxy();
        try {
            System.out.println(calcWithPP.calc(10));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
