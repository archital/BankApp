package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class TransferCommand implements Command {
    @Override
    public void execute()  {
        if (BankCommander.currentBank == null) {
            System.out.println("Error!!! Current bank is undefined");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        Client client = null;
        int amount = 0;


            client = BankCommander.currentClient;


            if (client == null) {
                System.out.println("Error!!! Client with such name was not found.");
                return;
            }
            if(client.getAccounts().isEmpty()) {
                System.out.println("Client: " + client.getGender().getGenderPrefix()+client.getName()+"haven't any accouns in Bank number "+ BankCommander.currentBank.getBankNumber());
                return;
            } else {
                System.out.println("Enter amount that you want to get from active account: ");
                amount =   Integer.parseInt(scanner.nextLine().trim());
                try {
                    client.getActiveAccount().withdraw(amount);
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }
                while (sb.length() == 0) {
                    System.out.println("Input client name whom you want to transfer money: ");
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
                else {

                    client.getActiveAccount().deposit(amount);
                }
            }


        }


    @Override
    public void printCommandInfo() {
        System.out.print("Transfer command");
    }
}
