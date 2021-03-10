package com.company.terminal;

import com.company.serverside.Exceptions.AccountAccessDeniedException;
import com.company.serverside.Exceptions.NotEnoughMoneyException;
import com.company.serverside.Exceptions.PasswordInitException;
import com.company.serverside.TerminalServer;
import com.company.terminal.PinValidator.Exceptions.AccountIsLockedException;
import com.company.terminal.PinValidator.Exceptions.WrongInputException;
import com.company.terminal.PinValidator.Exceptions.WrongPasswordException;
import com.company.terminal.PinValidator.PinValidator;

import java.math.BigDecimal;

public class TerminalImpl {

    private final TerminalServer terminalServer;
    private final PinValidator pinValidator;

    public TerminalImpl() throws UserMessageException {
        try {
            this.terminalServer = new TerminalServer();
        } catch (PasswordInitException e) {
            throw new UserMessageException("Внутренняя критическая ошибка системы, обратитесь к администратору (код 02)");
        }
        this.pinValidator = new PinValidator(terminalServer);
    }

    public boolean PinInputByNumbers(String numberOfPin) throws UserMessageException {
        try {
            pinValidator.receiveNumOfPassword(numberOfPin);
        } catch (WrongInputException e) {
            if(e.getType()==1){
                throw new UserMessageException("Введенный символ не является цифрой, повторите снова с цифрой");
            }
            if(e.getType()==2){
                throw new UserMessageException("Недопустимая цифра, повторите снова (от 0 до 9)");
            } else {
                throw new UserMessageException("Внутренняя ошибка системы, обратитесь к администратору (код 01)");
            }
        } catch (WrongPasswordException e2) {
            if(e2.getRemainingAttempts() == 0){
                throw new UserMessageException("Неверный пароль! Осталось попыток: " + e2.getRemainingAttempts() +
                        "\nАккаунт заблокирован на 10 секунд!");
            }
            throw new UserMessageException("Неверный пароль! Осталось попыток: " + e2.getRemainingAttempts());
        } catch (AccountIsLockedException e3) {
            throw new UserMessageException("Аккаунт заблокирован! До разблокировки осталось " + e3.getTimeUntilUnlock() + " сек.");
        } catch (PasswordInitException e4) {
            throw new UserMessageException("Внутренняя ошибка системы, обратитесь к администратору (код 0" + e4.getType() + ")");
        }
        return pinValidator.isLogIn();
    }

    public int getCurrentPositionOfPinInput(){
        return pinValidator.getReceiveNumOfPasswordCounter()+1;
    }

    public void logOut(){
        pinValidator.logOut();
    }

    public BigDecimal getCreditsValue() throws UserMessageException {
        try {
            return terminalServer.getCreditsValue();
        } catch (AccountAccessDeniedException e) {
            throw new UserMessageException("Доступ заблокирован! Сначала войдите в аккаунт");
        }
    }

    public void cashOut(String value) throws UserMessageException {
        BigDecimal bgValue;
        try {
            bgValue = new BigDecimal(value);
        } catch (NumberFormatException e){
            throw new UserMessageException("Неверно введена сумма!");
        }
        if (bgValue.remainder(new BigDecimal(100)).compareTo(BigDecimal.ZERO) == 0 & (bgValue.compareTo(BigDecimal.ZERO) > 0)) {
            try {
                terminalServer.cashOut(bgValue);
            } catch (AccountAccessDeniedException e) {
                throw new UserMessageException("Доступ заблокирован! Сначала войдите в аккаунт");
            } catch (NotEnoughMoneyException e2) {
                throw new UserMessageException("Недостаточно денег на счету!");
            }
        } else {
            throw new UserMessageException("Ошибка: введенная сумма не кратна 100 или не положительна!");
        }
    }

    public void cashIn(String value) throws UserMessageException {
        BigDecimal bgValue;
        try {
            bgValue = new BigDecimal(value);
        } catch (NumberFormatException e){
            throw new UserMessageException("Неверно введена сумма!");
        }
        if (bgValue.remainder(new BigDecimal(100)).compareTo(BigDecimal.ZERO) == 0 & (bgValue.compareTo(BigDecimal.ZERO) > 0)){
            try {
                terminalServer.cashIn(bgValue);
            } catch (AccountAccessDeniedException e) {
                throw new UserMessageException("Доступ заблокирован! Сначала войдите в аккаунт");
            }
        } else {
            throw new UserMessageException("Ошибка: введенная сумма не кратна 100 или не положительна!");
        }
    }

}
