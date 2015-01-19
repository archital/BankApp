package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Client;

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
    client = BankCommander.service.findClient(BankCommander.currentBank, name.toString());
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
