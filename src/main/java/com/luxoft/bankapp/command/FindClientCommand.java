package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.BankServer;
import com.luxoft.bankapp.server.Current;
import com.luxoft.bankapp.server.CurrentImpl;
import com.luxoft.bankapp.service.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class FindClientCommand  extends AbstractCommand implements Command {

    private InputOutput ioStreams;
    private Bank currentBank;
    private Integer accId;
    private Account currentAccount;
    private Current current;
    private Client currentClient;

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    public FindClientCommand(InputOutput inputOutput, Bank currentBank) {
    this.ioStreams = inputOutput;
    this.currentBank = currentBank;

    }

    public FindClientCommand(InputOutput io) {
      this.ioStreams = io;
    }

    public FindClientCommand() {
    }


    @Override
    public void execute() {

        currentBank = getCurrentBank();
        if (currentBank == null) {
            ioStreams.println("Error!!! Current bank is undefined.");
            return;
        }

        ioStreams.println("Input client name: ");
           String name = ioStreams.readln().trim();


        Client client = null;
        ClientService clientService = ServiceFactory.getClientImpl();
        AccountService accountService = ServiceFactory.getAccountImpl();


        try {
            client = clientService.findClientInDB(currentBank, name);


        } catch (ClientNotFoundException e) {
            ioStreams.println("Client with such name was not found ");
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

        setCurrentClient(client);
        BankServer.currentClient = client;

        current = new CurrentImpl();
       current.setCurrentClient(client);

        ioStreams.println("Current client "+client.toString() + "\n you can select new command " +
                "Current account is selected:\n " +
                currentAccount.toString() +
                "\npress 'Enter' ");
    }

    @Override
    public void printCommandInfo() {
        ioStreams.println("Find Client");
    }
}
