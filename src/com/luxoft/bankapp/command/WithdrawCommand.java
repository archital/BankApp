package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

/**
 * Created by acer on 15.01.2015.
 */
public class WithdrawCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private float amount = 0;

    public WithdrawCommand (InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public WithdrawCommand () {
    }

    @Override
    public void execute() {
        if (currentBank == null) {
            inOut.println("Error!!! Current bank is undefined.");
            return;
        }

            if (currentClient == null) {
                inOut.println("Error! Client with such name was not found.");
                return;
            }
            if (currentClient.getAccounts().isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getBankNumber());
                return;
            } else {
                inOut.println("Enter amount that you want to get: ");
                amount = Float.parseFloat(inOut.readln());

                try {
                    currentClient.getActiveAccount().withdraw(amount);
                    inOut.println("Withdraw successful! enter 'back'/ 'exit' or 'bye' ");
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void printCommandInfo() {
        inOut.println("WithDraw command ");
    }
}
