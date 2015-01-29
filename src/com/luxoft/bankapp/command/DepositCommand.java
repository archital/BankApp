package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;

/**
 * Created by acer on 15.01.2015.
 */
public class DepositCommand implements Command {


    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;


    public DepositCommand(InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public DepositCommand(InputOutput inOut) {

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
                    inOut.println("Enter amount that you want to put to active account: ");

                   Float amount = Float.parseFloat(inOut.readln());

                    currentClient.getActiveAccount().deposit(amount);

                 //   inOut.println(currentClient.toString() + "\n enter  'back'/ 'exit' or 'bye'");
                inOut.println("Deposit successful! enter new command ");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            accountDAO.save(currentClient.getActiveAccount(), currentClient); //write update to DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void printCommandInfo() {
        inOut.println("Deposit to Client's account");
    }
}
