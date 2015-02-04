package com.luxoft.bankapp.dao;

<<<<<<< HEAD
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.expeption.DAOException;
=======
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.DAOException;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface ClientDAO {
    Client findClientByName(Bank bank, String name) throws ClientNotFoundException, SQLException;
    Client findClientById(Integer clientId) throws ClientNotFoundException, SQLException, ClientExistsException;
    Set<Client> getAllClients(Bank bank) throws SQLException;
    void save(Client client, Integer bankId) throws SQLException, DAOException;
    void remove(Client client) throws SQLException;
}
