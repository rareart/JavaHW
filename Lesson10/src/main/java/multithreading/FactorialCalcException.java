package multithreading;

public class FactorialCalcException extends Exception {
    public FactorialCalcException() {
        super();
    }

    public FactorialCalcException(String message) {
        super(message);
    }

    public FactorialCalcException(String message, Throwable cause) {
        super(message, cause);
    }

    public FactorialCalcException(Throwable cause) {
        super(cause);
    }

    protected FactorialCalcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
