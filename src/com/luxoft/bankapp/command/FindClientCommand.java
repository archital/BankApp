package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.*;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.CommanderServer;
import com.luxoft.bankapp.service.AccountImpl;
import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.ClientImpl;
import com.luxoft.bankapp.service.ClientService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class FindClientCommand implements Command {

    private InputOutput ioStreams;
    private Bank currentBank;
    private Integer accId;
    private Account currentAccount;


    public FindClientCommand(InputOutput inputOutput, Bank currentBank) {
    this.ioStreams = inputOutput;
    this.currentBank = currentBank;

    }

    public FindClientCommand(InputOutput io) {
      this.ioStreams = io;
    }


    @Override
    public void execute() {

        if (currentBank == null) {
            ioStreams.println("Error!!! Current bank is undefined.");
            return;
        }

        ioStreams.println("Input client name: ");
           String name = ioStreams.readln().trim();


        Client client = null;
        ClientService clientService = new ClientImpl();
        AccountService accountService = new AccountImpl();


        try {
            client = clientService.findClientInDB(currentBank, name);


        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (client == null) {
            ioStreams.println("Error!!! Client with such name was not found.");
            return;
        }


        try {
            List<Account> accountList =  accountService.getClientAccounts(client.getId());

            ioStreams.println("Enter account ID to make this account 'current': \n" +
                    accountList.toString());


            accId = Integer.parseInt(ioStreams.readln());
            currentAccount = accountService.getAccountById(accId);


            client.setActiveAccount(currentAccount);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        BankCommander.currentClient = client; // set currentClient to BankCommander
        CommanderServer.currentClient = client;


        ioStreams.println("Current client "+client.toString() + "\n you can select new command " +
                "Current account is selected:\n " +
                currentAccount.toString() +
                "\npress 'Enter' for CommanderServer ");
    }

    @Override
    public void printCommandInfo() {
        ioStreams.println("Find Client");
    }
}
