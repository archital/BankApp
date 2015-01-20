package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Report;

import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        if (BankCommander.currentBank == null) {
            System.out.println("Error!!! Current bank is undefined.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        Client client = null;

        System.out.println("Do you want Current Client to get accounts (yes/no)");
        if(scanner.nextLine().trim().equals("yes")){
            client = BankCommander.currentClient;


            if (client == null) {
                System.out.println("Error! Client with such name was not found.\n");
                return;
            }
            if(client.getAccounts().isEmpty()) {
                System.out.println("Client: " + client.getGender().getGenderPrefix()+client.getName()+"haven't any accouns in Bank number "+ BankCommander.currentBank.getBankNumber());
            } else {
                for (Report account : client.getAccounts()) {
                    account.printReport();
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
            System.out.println("Error! Client with such name was not found.\n");
            return;
        }
        if(client.getAccounts().isEmpty()) {
            System.out.println("Client: " + client.getGender()+client.getName()+"haven't any accouns in Bank number "+ BankCommander.currentBank.getBankNumber());
        } else {
            for (Report account : client.getAccounts()) {
                account.printReport();
            }
        }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Get client's accounts ");
    }
}
