package com.company.terminal.PinValidator.Exceptions;

public class WrongPasswordException extends Exception {
    private int wrongAttemptsCounter;

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message, int wrongAttemptsCounter){
        super(message);
        this.wrongAttemptsCounter = wrongAttemptsCounter;
    }

    public WrongPasswordException(String message, Throwable cause, int wrongAttemptsCounter) {
        super(message, cause);
        this.wrongAttemptsCounter = wrongAttemptsCounter;
    }

    public WrongPasswordException(Throwable cause) {
        super(cause);
    }

    public WrongPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getRemainingAttempts(){
        return 3 - wrongAttemptsCounter;
    }
}
