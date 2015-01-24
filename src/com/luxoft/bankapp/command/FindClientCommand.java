package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.CommanderServer;
import com.luxoft.bankapp.service.BankImpl;

import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class FindClientCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;


    public FindClientCommand (InputOutput inputOutput, Bank currentBank) {
    this.inOut = inputOutput;
    this.currentBank = currentBank;

    }

    public FindClientCommand () {
    }


    @Override
    public void execute() {
        if (currentBank == null) {
            inOut.println("Error!!! Current bank is undefined.");
            return;
        }

                inOut.println("Input client name: ");
           String name = inOut.readln().trim();


        Client client = null;
        BankImpl bankImp = new BankImpl();
        try {
            client = bankImp.getClient(currentBank, name);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        if (client == null) {
            inOut.println("Error!!! Client with such name was not found.");
            return;
        }
        CommanderServer.currentClient = client;
        System.out.println("Client is selected: ");
        inOut.println(client.toString() + "\n enter new command 'back'/ 'exit' or 'bye'");
    }

    @Override
    public void printCommandInfo() {
        inOut.println("Find Client");
    }
}
