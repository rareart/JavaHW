package JDBC_example.connection;

public class DBInitException extends Exception{
    public DBInitException(String message) {
        super(message);
    }

    public DBInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBInitException(Throwable cause) {
        super(cause);
    }

    public DBInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
