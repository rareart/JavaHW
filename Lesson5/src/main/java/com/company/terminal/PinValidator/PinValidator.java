package com.company.terminal.PinValidator;

import com.company.serverside.Exceptions.PasswordInitException;
import com.company.serverside.TerminalServer;
import com.company.terminal.PinValidator.Exceptions.AccountIsLockedException;
import com.company.terminal.PinValidator.Exceptions.WrongInputException;
import com.company.terminal.PinValidator.Exceptions.WrongPasswordException;

public class PinValidator {
    private final TerminalServer terminalServer;
    private final char[] password;
    private long accountLockTimestamp;
    private int receiveNumOfPasswordCounter;
    private int wrongPinCounter;
    private boolean logInFlag;

    public PinValidator(TerminalServer terminalServer) {
        this.terminalServer = terminalServer;
        this.password = new char[4];
        receiveNumOfPasswordCounter = 0;
        wrongPinCounter = 0;
        logInFlag = false;
    }

    public void receiveNumOfPassword(String rawInput) throws WrongInputException, WrongPasswordException, AccountIsLockedException, PasswordInitException {
        if(wrongPinCounter>=3 && ((System.currentTimeMillis() / 1000L - accountLockTimestamp) < 10)) {
            throw new AccountIsLockedException("Account is locked!", System.currentTimeMillis() / 1000L - accountLockTimestamp);
        }
        int tmpRawNumber;
        //err_code_01 - unhandled exception
        try {
            tmpRawNumber = Integer.parseInt(rawInput);
        } catch (NumberFormatException e) {
            throw new WrongInputException("Input symbol is not a number, try again with number", e, 1);
        }
        if (tmpRawNumber < 0 || tmpRawNumber > 9) {
            throw new WrongInputException("Incorrect number, try again (with 0-9)", 2);
        } else {
            password[receiveNumOfPasswordCounter] = Character.forDigit(tmpRawNumber, 10);
            receiveNumOfPasswordCounter++;
        }

        if(receiveNumOfPasswordCounter>=4) {
            receiveNumOfPasswordCounter = 0;

            if (wrongPinCounter >= 3) {
                wrongPinCounter = 0;
            }
            if (terminalServer.passwordIsValid(password)) {
                logInFlag = true;
            } else {
                wrongPinCounter++;
                if (wrongPinCounter >= 3) {
                    accountLockTimestamp = System.currentTimeMillis() / 1000L;
                }
                throw new WrongPasswordException("Wrong password", wrongPinCounter);
            }
        }

    }

    public int getReceiveNumOfPasswordCounter(){
        return receiveNumOfPasswordCounter;
    }

    public void resetReceiveNumOfPassword(){
        receiveNumOfPasswordCounter = 0;
    }

    public boolean isLogIn(){
        return logInFlag;
    }

    public void logOut(){
        logInFlag = false;
        terminalServer.closeCurrentSession();
    }
}
