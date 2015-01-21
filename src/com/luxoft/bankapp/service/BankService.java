package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BankService {
    public void addClient(Bank bank, Client client) throws ClientExistsException, ClientExistsException;

    public void removeClient(Bank bank, Client client);

    public void addAccount(Client client, Account account) throws ClientExistsException;

    public void setActiveAccount(Client client, Account account);

    public Client findClient(Bank bank, String name);

    public void saveClient(Client client) throws IOException;

    public void loadClient(Client client) throws IOException, ClassNotFoundException;
}
