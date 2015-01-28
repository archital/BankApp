package com.luxoft.bankapp.server.command;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

/**
 * Created by acer on 15.01.2015.
 */
public class ServerDepositCommand implements Command {


    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;


    public ServerDepositCommand (InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public ServerDepositCommand () {
    }

    @Override
    public void execute() {
            if (currentBank == null) {
                inOut.println("Error!!! Current bank is undefined.");
                return;
            }

            if (currentClient.getAccounts().isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {
                inOut.println("Enter amount that you want to put to active account: ");

               Float amount = Float.parseFloat(inOut.readln());

                currentClient.getActiveAccount().deposit(amount);

                inOut.println(currentClient.toString() + "\n enter  'back'/ 'exit' or 'bye'");
            }


        }


    @Override
    public void printCommandInfo() {
        inOut.println("Deposit to Client's account");
    }
}
