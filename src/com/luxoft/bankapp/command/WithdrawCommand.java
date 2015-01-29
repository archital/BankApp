package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;

/**
 * Created by acer on 15.01.2015.
 */
public class WithdrawCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private float amount = 0;

    public WithdrawCommand(InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public WithdrawCommand(InputOutput inOut) {

        this.inOut = inOut;
    }

    @Override
    public void execute() {

        currentClient = BankCommander.currentClient;

        ClientDAO clientDAO = new ClientDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();

        if (currentBank == null) {
            inOut.println("Error!!! Current bank is undefined.");
            return;
        }

            if (currentClient == null) {
                inOut.println("Error! Client with such name was not found.");
                return;
            }
        try {
            if (accountDAO.getClientAccounts(currentClient.getId()).isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {
                inOut.println("Enter amount that you want to get: ");
                amount = Float.parseFloat(inOut.readln());

                try {
                    currentClient.getActiveAccount().withdraw(amount);
                   // inOut.println("Withdraw successful! enter 'back'/ 'exit' or 'bye' ");
                    inOut.println("Withdraw successful! enter new command ");

                    try {
                        accountDAO.save(currentClient.getActiveAccount(), currentClient); //write update to DB
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        inOut.println("WithDraw command ");
    }
}
