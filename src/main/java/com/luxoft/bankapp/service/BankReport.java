package com.luxoft.bankapp.service;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by SCJP on 19.01.2015.
 */
public class BankReport {


    public long getNumberOfClients(Bank bank) {

        long l = 0;
        for (Client c : bank.getClients()) {
            l++;
        }

        return l;
    }

    public long getAccountsNumber(Bank bank) {
        long l = 0;
        for (Client c : bank.getClients()) {
            for (Account account : c.getAccounts()) {
                l++;
            }
        }

        return l;
    }

    public Set<Client>  getClientsSorted(Bank bank) {


        Set<Client> clients = new TreeSet<Client>(new Comparator<Client>() { // ASC


            @Override
            public int compare(Client o1, Client o2) {
                if (o1.getBalance() == o2.getBalance()) {
                    return 0;
                }
                if (o1.getBalance() < o2.getBalance()) {
                    return -1;
                } else {
                    return +1;
                }
            }
        });
        for (Client cl : bank.getClients()) {
            Client client = cl;
            clients.add(client);
        }

        for (Client c : clients) {


        }

        return clients;
    }

    public float getBankCreditSum(Bank bank) {
        float resultSum = 0;
        for (Client cl : bank.getClients()) {
            for (Account account : cl.getAccounts()) {
                if (account.getBalance() < 0) {
                    resultSum = resultSum + account.getBalance();
                }
            }
        }

        return resultSum;
    }


    public Map<String, List<Client>> getClientsByCity(Bank bank) {

        Map<String, List<Client>> listMap = new TreeMap();

        ClientService clientService = ServiceFactory.getClientImpl();


        Set<Client> clients = null;
        try {
            try {
                clients = clientService.getAllClients(bank);
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Client client : clients) {
            List<Client> clientList = listMap.get(client.getCity());
            if (clientList == null) {
                clientList = new ArrayList();
            }
            clientList.add(client);
            listMap.put(client.getCity(), clientList);
        }

        return listMap;
    }

}