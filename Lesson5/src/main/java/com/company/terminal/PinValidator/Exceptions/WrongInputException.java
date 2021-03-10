package com.company.terminal.PinValidator.Exceptions;

public class WrongInputException extends Exception {

    int type;

    public WrongInputException() {
    }

    public WrongInputException(String message, int type) {
        super(message);
        this.type = type;
    }

    public WrongInputException(String message, Throwable cause, int type) {
        super(message, cause);
        this.type = type;
    }

    public WrongInputException(Throwable cause) {
        super(cause);
    }

    public WrongInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getType() {
        return type;
    }
}
