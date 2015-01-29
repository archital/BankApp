package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface ClientDAO {
    Client findClientByName(Bank bank, String name) throws ClientNotFoundException, SQLException;
    Client findClientById(int clientId) throws ClientNotFoundException, SQLException, ClientExistsException;
    Set<Client> getAllClients(Bank bank) throws SQLException;
    void save(Client client, int bankId) throws SQLException;
    void remove(Client client) throws SQLException;
}
