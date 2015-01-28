package com.luxoft.bankapp.server.command;

import com.luxoft.bankapp.command.BankCommander;
import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;

/**
 * Created by acer on 15.01.2015.
 */
public class ServerTransferCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private float amount = 0;


    public ServerTransferCommand (InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public ServerTransferCommand () {
    }

    @Override
    public void execute() {

        BankImpl bankImp = new BankImpl();

        if (BankCommander.currentBank == null) {
           inOut.println("Error!!! Current bank is undefined");
            return;
        }
        if (currentClient == null) {
            inOut.println("Error!!! Client with such name was not found.");
            return;
        }
        if (currentClient.getAccounts().isEmpty()) {
            inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any account in Bank number " + currentBank.getId());
            return;
        } else{
            inOut.println("Enter amount that you want to get from active account: ");
            amount = Float.parseFloat(inOut.readln());
            try {
                currentClient.getActiveAccount().withdraw(amount);
            } catch (NotEnoughFundsException e) {
                e.printStackTrace();
            }

                inOut.println("Input client name whom you want to transfer money: ");

            String clientName = inOut.readln();

             Client client = null;
            client = bankImp.findClient(currentBank, clientName);
            if (client == null) {
                inOut.println("Error!!! Client with such name was not found.");
                return;
            } else {

                client.getActiveAccount().deposit(amount);
                inOut.println(client.toString()+ "\n Successful transfer, enter 'back'/ 'exit' or 'bye' ");
            }
        }


    }


    @Override
    public void printCommandInfo() {
        inOut.println("Transfer command");
    }
}
