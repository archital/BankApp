package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Report;

import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        if (BankCommander.currentBank == null) {
            System.out.println("Error!!! Current bank is undefined.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        Client client = null;
        int amount = 0;

        System.out.println("Do you want to get money from Current Client account (yes/no)");
        if(scanner.nextLine().trim().equals("yes")){
            client = BankCommander.currentClient;


            if (client == null) {
                System.out.println("Error! Client with such name was not found.");
                return;
            }
            if(client.getAccounts().isEmpty()) {
                System.out.println("Client: " + client.getGender().getGenderPrefix()+client.getName()+"haven't any accouns in Bank number "+ BankCommander.currentBank.getBankNumber());
                return;
            } else {
                System.out.println("Enter amount that you want to get: ");
              amount =   Integer.parseInt(scanner.nextLine().trim());

                try {
                    client.getActiveAccount().withdraw(amount);
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }
            }


        }
        else {
            while (sb.length() == 0) {
                System.out.println("Input client name: ");
                sb.delete(0, sb.length());
                sb.append(scanner.nextLine().trim());
            }
            String clientName = sb.toString();
            sb.delete(0, sb.length());


            client = BankCommander.service.findClient(BankCommander.currentBank, clientName.toString());
            if (client == null) {
                System.out.println("Error!!! Client with such name was not found.");
                return;
            }
            if(client.getAccounts().isEmpty()) {
                System.out.println("Client: " + client.getGender()+client.getName()+"haven't any accouns in Bank number "+ BankCommander.currentBank.getBankNumber());
                return;
            } else { System.out.println("Enter amount that you want to get: ");
                amount =   Integer.parseInt(scanner.nextLine().trim());
                try {
                    client.getActiveAccount().withdraw(amount);
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("WithDraw command ");
    }
}
