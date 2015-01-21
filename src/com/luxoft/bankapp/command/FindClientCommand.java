package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankReport;

import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class FindClientCommand implements Command {
    @Override
    public void execute() {
        if (BankCommander.currentBank == null) {
            System.out.println("Error!!! Current bank is undefined.");
            return;
        }

        StringBuilder name = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        while (name.length() == 0) {
            System.out.println("Input client name: ");
            name.delete(0, name.length());
            name.append(scanner.nextLine().trim());
        }
        Client client = null;
        BankImpl bankImp = new BankImpl();
        try {
            client = bankImp.getClient(BankCommander.currentBank, name.toString());
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        if (client == null) {
            System.out.println("Error!!! Client with such name was not found.");
            return;
        }
        BankCommander.currentClient = client;
        System.out.println("Client is selected: ");
        System.out.println(client.toString());
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Find Client");
    }
}
