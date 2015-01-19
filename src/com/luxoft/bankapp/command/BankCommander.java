package com.luxoft.bankapp.command;

import com.luxoft.bankapp.BankApplication;
import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.expeption.BankException;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class BankCommander {
    public static Bank currentBank; // = new Bank(1);
    public static Client currentClient = null;
    public static BankService service = new BankImpl();



   static {
        // first client
        Account account1ForClient1 = new SavingAccount(10,100);
        Account account2ForClient1 = new CheckingAccount(2, 50);
        Client client1 = new Client();
        client1.setName("Peter");
        client1.setGender(Gender.MALE);
       client1.setEmail("qq@mail.ru");
       client1.setTelephoneNumber("+380953434243");
        client1.setActiveAccount(account1ForClient1);

        // second client
        Account account1ForClient2 = new SavingAccount(3, 20);
        Account account2ForClient2 = new CheckingAccount(4, 35);
        Client client2 = new Client();
        client2.setName("Ludmila");
        client2.setGender(Gender.FEMALE);
       client2.setEmail("qq@gmail.com");
       client2.setTelephoneNumber("+380952342243");
        client2.setActiveAccount(account1ForClient2);


        List<ClientRegistrationListener> listeners = new ArrayList();

       currentBank = new Bank(1);
       currentBank = new Bank(listeners);
       currentBank.setBankNumber(1);
        BankService bankService = new BankImpl();
        try {
            bankService.addClient(currentBank, client1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addClient(currentBank, client2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client1, account1ForClient1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client1, account2ForClient2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client2, account1ForClient2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client2, account2ForClient1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        System.out.println("///////////////Client1 toString////////////");
        System.out.println(client1.toString());
        System.out.println("///////////////Client2 toString////////////");
        System.out.println(client2.toString());
    }

    static Command[] commands = {
            new FindClientCommand(), // 0
            new GetAccountsCommand(), // 1
            new WithdrawCommand(), //2
            new DepositCommand(), //3
            new TransferCommand(), //4
            new AddClientCommand(), //5
            new Command() { // 6 - Exit Command
                public void execute() {
                    System.exit(0);
                }
                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < commands.length; i++) {
            System.out.print(i + "/ ");
            commands[i].printCommandInfo();
            System.out.print("\n");
        }
        int command;

        while (true) {
            while (sb.length() == 0) {
                System.out.println("Input command number: ");
                sb.append(scanner.nextLine().trim());
            }
            try {
                command = Integer.parseInt(sb.toString());
            } catch (RuntimeException e) {
                System.out.println("Error! Illegal number value.\n");
                return;
            }
            sb.delete(0, sb.length());
            System.out.print("Executing command ");
            commands[command].printCommandInfo();
            System.out.print("...\n\n");
            try {
                commands[command].execute(); //start working command
            } catch (ClientExistsException e) {
                e.printStackTrace();
            }
        }
    }
}
