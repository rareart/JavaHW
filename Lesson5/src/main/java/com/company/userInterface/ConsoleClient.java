package com.company.userInterface;

import com.company.terminal.TerminalImpl;
import com.company.terminal.UserMessageException;

import java.util.Scanner;

public class ConsoleClient {

    private final TerminalImpl terminal;
    private boolean isActive;

    public ConsoleClient() {
        isActive = true;
        try {
            this.terminal = new TerminalImpl();
        } catch (UserMessageException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void menu(){
        Scanner cmdInput = new Scanner(System.in);
        logIn(cmdInput);
        while (isActive){
            switchMenu(cmdInput);
            System.out.println("----------------------------------------");
        }
    }

    private void logIn(Scanner cmdInput){
        System.out.println("Введите четырехзначный пароль для входа.\nВвод каждой цифры с новой строки:");
        boolean status = false;
        while (!status){
            try {
                System.out.println("Введите " + terminal.getCurrentPositionOfPinInput() + " цифру пароля:");
                status = terminal.PinInputByNumbers(cmdInput.nextLine());
            } catch (UserMessageException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
        System.out.println("Вы успешно вошли в систему!");
    }

    private void switchMenu(Scanner cmdInput){
        System.out.println("Выберите номер необходимого пункта меню:");
        System.out.println("1 - узнать баланс, 2 - снять наличные, 3 - внести наличные,\n4 - логаут, 5 - ре-логин, 6 - завершить работу");
        String pos = cmdInput.nextLine();
        switch (pos){
            case "1":
                try {
                    System.out.println("Ваш баланс: " + terminal.getCreditsValue());
                } catch (UserMessageException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                try {
                    System.out.println("Введите сумму для снятия (кратную 100):");
                    terminal.cashOut(cmdInput.nextLine());
                } catch (UserMessageException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "3":
                System.out.println("Введите сумму для пополнения (кратную 100):");
                try {
                    terminal.cashIn(cmdInput.nextLine());
                } catch (UserMessageException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "4":
                terminal.logOut();
                System.out.println("Вы вышли из системы");
                break;
            case "5":
                terminal.logOut();
                logIn(cmdInput);
                break;
            case "6":
                terminal.logOut();
                isActive = false;
                System.out.println("Работа с терминалом завершена.");
                break;
            default:
                System.out.println("Ошибка: неверно выбран пункт меню. Повторите снова.");
                break;
        }
    }
}
