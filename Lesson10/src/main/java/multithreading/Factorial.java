package multithreading;

import java.math.BigInteger;

public class Factorial {
    public BigInteger getFactorial(BigInteger num){
        return ((num.compareTo(BigInteger.ONE) > 0) ?
                num.multiply(getFactorial(num.subtract(new BigInteger(String.valueOf(1))))) : BigInteger.ONE);
    }
}
