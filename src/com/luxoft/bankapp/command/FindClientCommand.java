package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.*;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.CommanderServer;
import com.luxoft.bankapp.service.BankImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class FindClientCommand implements Command {

    private InputOutput ioStreams;
    private Bank currentBank;


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
        ClientDAO clientDAO = new ClientDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();


        try {
            client = clientDAO.findClientByName(currentBank, name);


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
           List<Account> accounts = accountDAO.getClientAccounts(client.getId()); //set active account to CurrentClient

             client.setActiveAccount(accounts.get(accounts.size()-1));

            CommanderServer.currentClient = client;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        BankCommander.currentClient = client; // set currentClient to BankCommander

        System.out.println("Client is selected: ");
    //    ioStreams.println(client.toString() + "\n enter new command 'back'/ 'exit' or 'bye'"); for CommanderServer
        ioStreams.println(client.toString() + "\n enter new command");
    }

    @Override
    public void printCommandInfo() {
        ioStreams.println("Find Client");
    }
}
