package com.luxoft.bankapp.command;

import com.luxoft.bankapp.BankApplication;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

/**
 * Created by acer on 15.01.2015.
 */
public class BankCommander {
    public static Bank currentBank = new Bank();
    public static Client currentClient;
    public static BankApplication bankApplication = new BankApplication();

    static Command[] commands = {
            new FindClientCommand(), // 1
            new GetAccountsCommand(), // 2
            new WithdrawCommand(), //3
            new DepositCommand(), //4
            new TransferCommand(), //5
            new AddClientCommand(), //6
            new Command() { // 7 - Exit Command
                public void execute() {
                    System.exit(0);
                }
                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String args[]) {
        bankApplication.initialize();
        currentBank = bankApplication.
        while (true) {
            for (int i=0;i<commands.length;i++) { // show menu
                System.out.print(i+") ");
                commands[i].printCommandInfo();
            }
            String commandString = System.console().readLine();
            int command=0; // initialize command with commandString
            commands[command].execute();
        }
    }
}
