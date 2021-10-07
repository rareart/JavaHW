package terminal.Exceptions;

public class PasswordInitException extends Exception{

    private int type = 0;

    public PasswordInitException() {
    }

    public PasswordInitException(String message, int type) {
        super(message);
        this.type = type;
    }

    public PasswordInitException(String message, Throwable cause, int type) {
        super(message, cause);
        this.type = type;
    }

    public PasswordInitException(Throwable cause) {
        super(cause);
    }

    public PasswordInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getType() {
        return type;
    }
}
