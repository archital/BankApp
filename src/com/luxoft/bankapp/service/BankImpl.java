package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankImpl implements BankService {
    @Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {

        for (Client c: bank.getClients()){
            if ( c.getName().equals(client.getName())){
                throw new ClientExistsException("Client with that name already exists");
            }
        }
        bank.addClient(client);
    }


    @Override
    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
    }

    @Override
    public void addAccount(Client client, Account account) throws ClientExistsException{
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }
}

