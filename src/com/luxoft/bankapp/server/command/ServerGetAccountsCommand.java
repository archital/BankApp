package com.luxoft.bankapp.server.command;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Report;

/**
 * Created by acer on 15.01.2015.
 */
public class ServerGetAccountsCommand implements Command {
    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;



    public ServerGetAccountsCommand (InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public ServerGetAccountsCommand () {
    }

    @Override
    public void execute() {
        if (currentBank == null) {
           inOut.println("Error!!! Current bank is undefined.");
            return;
        }

           if (currentClient == null) {
                inOut.println("Error! Client with such name was not found.\n");
                return;
            }
            if (currentClient.getAccounts().isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {

                StringBuilder stringBuilder = new StringBuilder();

                for (Report account : currentClient.getAccounts()) {
                stringBuilder.append(account.toString());
                }
                inOut.println(stringBuilder.toString()+ "\n enter 'back'/ 'exit' or 'bye'");
            }



    }

    @Override
    public void printCommandInfo() {
        inOut.println("Get client's accounts ");
    }
}
