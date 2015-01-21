package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import javax.xml.transform.sax.SAXSource;
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
        System.out.println("Number of clients " + l);
        return l;
    }

    public long getAccountsNumber(Bank bank) {
        long l = 0;
        for (Client c : bank.getClients()) {
            for (Account account : c.getAccounts()) {
                l++;
            }
        }
        System.out.println("Number of Accounts " + l);
        return l;
    }

    public String getClientsSorted(Bank bank) {


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
            System.out.println("Client :" + c.toString());
            return "Client"+ c.toString();
        }

        return null;
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
        System.out.println("Total sum that client's get more than limit is: " + resultSum);
        return resultSum;
    }


    public Map<String, List<Client>> getClientsByCity(Bank bank) {

        Map<String, List<Client>> listMap = new TreeMap();
        Set<Client> clients = bank.getClients();
        for (Client client : clients) {
            List<Client> clientList = listMap.get(client.getCity());
            if (clientList == null) {
                clientList = new ArrayList();
            }
            clientList.add(client);
            listMap.put(client.getCity(), clientList);
        }
        System.out.println(listMap.toString());
        return listMap;
    }

}