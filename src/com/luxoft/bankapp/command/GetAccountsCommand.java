package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Report;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class GetAccountsCommand implements Command {
    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;



    public GetAccountsCommand(InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }


    public GetAccountsCommand(InputOutput inOut) {

        this.inOut = inOut;
    }

    @Override
    public void execute() {

        currentClient = BankCommander.currentClient;

        if (currentBank == null) {
           inOut.println("Error!!! Current bank is undefined.");
            return;
        }

           if (currentClient == null) {
                inOut.println("Error! Client with such name was not found.\n");
                return;
            }

        AccountDAO accountDAO = new AccountDAOImpl();
        try {
            if (accountDAO.getClientAccounts(currentClient.getId()).isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {

                StringBuilder stringBuilder = new StringBuilder();


                List<Account> accounts = accountDAO.getClientAccounts(currentClient.getId());
                for (Account account : accounts) {
                stringBuilder.append(account.toString());
                }
              //  inOut.println(stringBuilder.toString()+ "\n enter 'back'/ 'exit' or 'bye'");
                inOut.println(stringBuilder.toString()+ "\n enter new command");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void printCommandInfo() {
        inOut.println("Get client's accounts ");
    }
}
