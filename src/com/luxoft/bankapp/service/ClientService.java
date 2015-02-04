package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by SCJP on 29.01.2015.
 */
public interface ClientService {
    public void addClient(Bank bank, Client client) throws SQLException, ClientExistsException, ClientExistsException;
    public void removeClient(Client client,Bank bank) throws SQLException;
    public Client findClientInDB(Bank bank, String name) throws SQLException, ClientNotFoundException;
    public void loadClientFromFile(Client client) throws IOException, ClassNotFoundException;
    public Client findClient(Bank bank, String name) throws SQLException, ClientNotFoundException;
    public void saveClientToFile(Client client) throws IOException;
    public float getBalance(Client client);
    public Client getClient(Bank bank, String clientName) throws ClientExistsException;
    public Set<Client> getAllClients(Bank bank) throws SQLException, ClientNotFoundException, ClientNotFoundException;
}
