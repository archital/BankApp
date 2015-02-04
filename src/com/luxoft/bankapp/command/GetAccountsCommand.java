package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
<<<<<<< HEAD
import com.luxoft.bankapp.service.*;
=======
import com.luxoft.bankapp.service.AccountImpl;
import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.ClientImpl;
import com.luxoft.bankapp.service.ClientService;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

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


        if (currentBank == null) {
           inOut.println("Error!!! Current bank is undefined.");
            return;
        }

           if (currentClient == null) {
                inOut.println("Error! Client with such name was not found.\n");
                return;
            }

<<<<<<< HEAD

        AccountService accountService = ServiceFactory.getAccountImpl();
=======
        ClientService clientService = new ClientImpl();
        AccountService accountService = new AccountImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

        try {
            if (accountService.getClientAccounts(currentClient.getId()).isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {

                StringBuilder stringBuilder = new StringBuilder();


                List<Account> accounts = accountService.getClientAccounts(currentClient.getId());
                for (Account account : accounts) {
                stringBuilder.append(account.toString());
                }

                inOut.println(stringBuilder.toString()+ "\n you can select new command +\n" +
                        "press 'Enter' for CommanderServer ");
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
