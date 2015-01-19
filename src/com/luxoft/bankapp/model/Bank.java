package com.luxoft.bankapp.model;

import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.expeption.ClientExistsException;
import sun.misc.Version;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SCJP on 14.01.2015.
 */
public class Bank {

    public List<ClientRegistrationListener> listeners = new ArrayList();

    private List<Client> clients = new ArrayList<Client>();

    public List<Client> getClients() {
        return clients;
    }

    private long bankNumber;

    public void addClient( Client client) throws ClientExistsException {
       for (Client c: getClients()){
           if ( c.getName().equals(client.getName())){
               throw new ClientExistsException("Client with that name already exists");
           }
        }
        clients.add(client);
        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    public void removeClient( Client client) {
        clients.remove(client);
    }

    public void addRegistrationListener(ClientRegistrationListener registrationListener) {
        listeners.add(registrationListener);
    };

    public void printReport() {
        System.out.println("Bank number " + getBankNumber() +" report: ");
        for (Client c: getClients()){
            System.out.println();
            c.printReport();
        }
    }
    public Bank(int bankNumber){
        super();
        this.bankNumber = bankNumber;
    }

    public long getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(long bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Bank() {
        this.listeners.add(new ClientRegistrationListener() {

            @Override
            public void onClientAdded(Client client) {
                System.out.println("New client added, " + client.getName()  + " " + new Date()+" to Bank number №"+ getBankNumber());
            }
        });
    }

    public Bank(List<ClientRegistrationListener> registrationListeners){
        this();
        this.listeners.addAll(registrationListeners);
    }

    public class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) { //распечатывает клиента на консоль
            System.out.println("Name: " + c.getGender().getGenderPrefix() + " " + c.getName());
            System.out.println("Overdraft: " + c.getInitialOverdraft());
            System.out.println("Balance:" + c.getBalance());
        }
    }


    public class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("Notification email for client " +  c.getGender().getGenderPrefix() + " " + c.getName() + "to be sent");
        }
    }


}