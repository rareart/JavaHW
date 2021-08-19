package task2;

public class DataListenerException extends Exception {

    public DataListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataListenerException(Throwable cause) {
        super(cause);
    }

    public DataListenerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
