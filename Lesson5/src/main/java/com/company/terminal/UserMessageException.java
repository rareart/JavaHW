package com.company.terminal;

public class UserMessageException extends Exception {
    public UserMessageException() {
    }

    public UserMessageException(String message) {
        super(message);
    }

    public UserMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserMessageException(Throwable cause) {
        super(cause);
    }

    public UserMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
