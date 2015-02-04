package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class DepositCommand implements Command {


    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private Account currentAccount;
    private float amount = 0;
    private  Integer accId = null;


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

        AccountService accountService = ServiceFactory.getAccountImpl();

            if (currentBank == null) {
                inOut.println("Error!!! Current bank is undefined.");
                return;
            }

        if (currentClient == null) {
            inOut.println("Error! Client with such name was not found.");
            return;
        }

        try {
            if (accountService.getClientAccounts(currentClient.getId()).isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {

                List<Account> accountList =  accountService.getClientAccounts(currentClient.getId());

                inOut.println("Enter account ID to make this account 'current': \n" +
                        accountList.toString());


                accId = Integer.parseInt(inOut.readln());
                currentAccount = accountService.getAccountById(accId);


                currentClient.setActiveAccount(currentAccount);

                inOut.println("Current account is selected:\n " +
                        currentAccount.toString()+
                        "\nEnter amount that you want to put to the active account: ");
                amount = Float.parseFloat(inOut.readln());


                accountService.deposit(amount, currentClient.getActiveAccount());


                inOut.println("Deposit successful! you can select new command" +
                        "\npress 'Enter' for CommanderServer ");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            accountService.addAccount(currentClient, currentClient.getActiveAccount()); //write update to DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void printCommandInfo() {
        inOut.println("Deposit to Client's account");
    }
}
