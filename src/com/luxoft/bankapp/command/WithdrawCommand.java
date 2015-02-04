package com.luxoft.bankapp.command;

<<<<<<< HEAD
import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.exception.OverDraftLimitExceededException;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.*;
=======
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.AccountImpl;
import com.luxoft.bankapp.service.AccountService;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class WithdrawCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private Account currentAccount;
    private float amount = 0;
    private  Integer accId = null;

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


<<<<<<< HEAD
        AccountService accountService = ServiceFactory.getAccountImpl();
=======
        AccountService accountService = new AccountImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

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
                        "\nEnter amount that you want to get: ");
                amount = Float.parseFloat(inOut.readln());

                try {
<<<<<<< HEAD

                    try {
                        accountService.withdraw(amount, currentClient.getActiveAccount());
                    } catch (OverDraftLimitExceededException e) {
                        inOut.println("Not enough money in this account");
                    }


                    inOut.println("Withdraw successful! you can select new command" +
                            "\npress 'Enter' for CommanderServer ");

                    try {
                        accountService.addAccount(currentClient, currentClient.getActiveAccount()); //write update to DB
=======

                    accountService.withdraw(amount, currentClient.getActiveAccount());


                    inOut.println("Withdraw successful! you can select new command" +
                            "\npress 'Enter' for CommanderServer ");

                    try {
                        try {
                            accountService.addAccount(currentClient, currentClient.getActiveAccount()); //write update to DB
                        } catch (ClientExistsException e) {
                            e.printStackTrace();
                        }
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (NotEnoughFundsException e) {
                    inOut.println("Not enough money in this account");
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
