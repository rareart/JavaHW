package com.company.serverside.Exceptions;

public class AccountAccessDeniedException extends Exception{
    public AccountAccessDeniedException() {
    }

    public AccountAccessDeniedException(String message) {
        super(message);
    }

    public AccountAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccountAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
