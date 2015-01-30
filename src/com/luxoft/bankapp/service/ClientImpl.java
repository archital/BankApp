package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.expeption.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by SCJP on 29.01.2015.
 */
public class ClientImpl implements ClientService{


    @Override
    public void addClient(Bank bank, Client client) throws SQLException, ClientExistsException {
        ClientDAO clientDAO = new ClientDAOImpl();

        try {
            clientDAO.save(client, bank.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }


        for (Client c : bank.getClients()) {
            if (c.getName().equals(client.getName())) {
                throw new ClientExistsException("Client with that name already exists");
            }
        }
        bank.addClient(client);
    }

    @Override
    public void removeClient(Client client, Bank bank) throws SQLException {
        ClientDAO clientDAO = new ClientDAOImpl();
        clientDAO.remove(client);
            bank.removeClient(client);
    }

    @Override
    public Client findClientInDB(Bank bank, String name) throws SQLException, ClientNotFoundException {
        ClientDAO clientDAO = new ClientDAOImpl();
        Client   client =  clientDAO.findClientByName(bank, name);
        return client;
    }

    @Override
    public Client findClient(Bank bank, String name) throws SQLException, ClientNotFoundException {
        Client findClient = null;


        for (Client c : bank.getClients()) {

            if (c.getName().equals(name)) {
                findClient = c;
                return findClient;
            }
        }

        if (findClient == null) {
            System.out.println("Person with that name is not a client of Bank" + bank.getId());
        }

        return null;
    }

    @Override
    public void saveClientToFile(Client client) throws IOException {


            String FilePath = "C:\\Users\\SCJP\\IdeaProjects\\Feed\\FeedObject";
            File file5 = new File(FilePath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file5));


            oos.writeObject(client);
            oos.close();


    }

    @Override
    public float getBalance(Client client) {
        float balance = 0;


        for (Account account : client.getAccounts()) {
            balance += account.getBalance();
        }
        return balance;
    }

    @Override
    public void loadClientFromFile(Client client) throws IOException, ClassNotFoundException {

            String FilePath = "C:\\Users\\SCJP\\IdeaProjects\\Feed\\FeedObject";
            File fileObj = new File(FilePath);

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileObj));
            Client obj = (Client) ois.readObject();
            System.out.println("Client = " + obj.toString());
            ois.close();

    }


    @Override
    public Client getClient(Bank bank, String clientName) throws ClientExistsException {

        Iterator iterator = bank.getClientMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Client> printMap = (Map.Entry<String, Client>) iterator.next();

            if (printMap.getKey().equals(clientName)) {
                return printMap.getValue();
            }
        }
        throw new ClientExistsException("Client wih such name was not found");
    }

    @Override
    public Set<Client> getAllClients (Bank bank) throws SQLException, ClientNotFoundException {
        ClientDAO clientDAO = new ClientDAOImpl();
       Set<Client> clients = clientDAO.getAllClients(bank);
        return clients;
    }


}