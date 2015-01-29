package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class TransferCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private float amount = 0;


    public TransferCommand(InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }




    public TransferCommand(InputOutput inOut) {

        this.inOut = inOut;
    }

    @Override
    public void execute() {


        currentClient = BankCommander.currentClient;

        ClientDAO clientDAO = new ClientDAOImpl();
        AccountDAO accountDAO = new AccountDAOImpl();


        if (BankCommander.currentBank == null) {
           inOut.println("Error!!! Current bank is undefined");
            return;
        }
        if (currentClient == null) {
            inOut.println("Error!!! Client with such name was not found.");
            return;
        }
        try {
            if (accountDAO.getClientAccounts(currentClient.getId()).isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            }else{
                inOut.println("Enter amount that you want to get from active account: ");
                amount = Float.parseFloat(inOut.readln());
                try {
                    currentClient.getActiveAccount().withdraw(amount);

                    accountDAO.save(currentClient.getActiveAccount(), currentClient); //write update to DB
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }

                    inOut.println("Input client name whom you want to transfer money: ");

                String clientName = inOut.readln();

                 Client client = null;
                try {
                    client = clientDAO.findClientByName(currentBank, clientName);
                } catch (ClientNotFoundException e) {
                    e.printStackTrace();
                }
                if (client == null) {
                    inOut.println("Error!!! Client with such name was not found.");
                    return;
                } else {


                    List<Account> accounts = accountDAO.getClientAccounts(client.getId()); //set active account to CurrentClient

                    client.setActiveAccount(accounts.get(accounts.size()-1));

                    client.getActiveAccount().deposit(amount);
                    accountDAO.save(client.getActiveAccount(), currentClient); //write update to DB

                    //    inOut.println(client.toString()+ "\n Successful transfer, enter 'back'/ 'exit' or 'bye' ");
                    inOut.println(client.toString()+ "\n Successful transfer, enter new command");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void printCommandInfo() {
        inOut.println("Transfer command");
    }
}
