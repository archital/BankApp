package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class BankImpl implements BankService {
    @Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {

        for (Client c : bank.getClients()) {
            if (c.getName().equals(client.getName())) {
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
    public void addAccount(Client client, Account account) throws ClientExistsException {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public Client findClient(Bank bank, String name) {
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
    public void saveClient(Client client) throws IOException {

        String FilePath = "C:\\Users\\SCJP\\IdeaProjects\\Feed\\FeedObject";
        File file5 = new File(FilePath);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file5));


        oos.writeObject(client);
        oos.close();
    }

    @Override
    public void loadClient(Client client) throws IOException, ClassNotFoundException {
        String FilePath = "C:\\Users\\SCJP\\IdeaProjects\\Feed\\FeedObject";
        File fileObj = new File(FilePath);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileObj));
        Client obj = (Client) ois.readObject();
        System.out.println("Client = " + obj.toString());
        ois.close();
    }

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

}
