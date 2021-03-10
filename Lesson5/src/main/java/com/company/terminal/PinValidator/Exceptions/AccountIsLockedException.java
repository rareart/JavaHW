package com.company.terminal.PinValidator.Exceptions;

public class AccountIsLockedException extends Exception {
    int timeUntilUnlock = 0;

    public AccountIsLockedException() {
    }

    public AccountIsLockedException(String message, long lockTime) {
        super(message);
        timeUntilUnlock = 10 - (int)lockTime;
    }

    public AccountIsLockedException(String message, Throwable cause, long lockTime) {
        super(message, cause);
        timeUntilUnlock = 10 - (int)lockTime;
    }

    public AccountIsLockedException(Throwable cause) {
        super(cause);
    }

    public AccountIsLockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getTimeUntilUnlock() {
        return timeUntilUnlock;
    }
}
