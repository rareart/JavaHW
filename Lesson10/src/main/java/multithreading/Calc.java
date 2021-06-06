package multithreading;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Iterator;

public class Calc {
    public void calcFactorial(String filePath, boolean logs) throws FactorialCalcException {
        Reader reader = new Reader(filePath);
        try {
            reader.fileInit();
        } catch (URISyntaxException e) {
            throw new FactorialCalcException("Wrong filepath", e);
        } catch (IOException e) {
            throw new FactorialCalcException("File reading error, e");
        }
        Iterator<Integer> numsIterator = reader.getNumsIterator();
        for(int i=0; i<reader.getNumberOfNums(); i++){
            new Thread(() -> {
                int num;
                synchronized (numsIterator){
                    num = numsIterator.next();
                }
                Factorial factorial = new Factorial();
                BigInteger result = factorial.getFactorial(new BigInteger(String.valueOf(num)));
                synchronized (System.out){
                    System.out.println("For " + num + " result is: " + result);
                    if(logs){
                        System.out.println("From " + Thread.currentThread().getName() + "\n");
                    }
                }
            }).start();
        }
    }
}
