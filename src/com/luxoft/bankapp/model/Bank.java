package com.luxoft.bankapp.model;

import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.annotation.annotation;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.FeedException;

import java.util.*;

/**
 * Created by SCJP on 14.01.2015.
 */
public class Bank {



   @annotation.NoDB private Integer id = null;

    private String name;

    @annotation.NoDB public List<ClientRegistrationListener> listeners = new ArrayList();


    private Set<Client> clients = new HashSet<Client>();


    @annotation.NoDB  private Map<String, Client> clientMap = new HashMap<String, Client>();


    public Set<Client> getClients() {
        return clients;
    }

    public void setClients (Set<Client> clients) {
        this.clients = clients;
    }

    public void parseFeed(Map<String, String> feedMap) throws IllegalArgumentException, FeedException {

        String name = feedMap.get("name"); // client name
        // try to find client by his name
        Client client = clientMap.get(name);
        if (client == null) { // if no client then create it
            client = new Client(name);
            clients.add(client);
            clientMap.put(name, client);
        }
        client.parseFeed(feedMap);
    }


    public void addClient(Client client) throws ClientExistsException {
        for (Client c : getClients()) {
            if (c.getName().equals(client.getName())) {
                throw new ClientExistsException("Client with that name already exists");
            }
        }
        clients.add(client);
        for (ClientRegistrationListener listener : listeners) {
           listener.onClientAdded(client);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void addRegistrationListener(ClientRegistrationListener registrationListener) {
        listeners.add(registrationListener);
    }


    public void printReport() {
        System.out.println( " report: ");
        for (Client c : getClients()) {
            System.out.println();
            c.printReport();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Bank() {
        this.listeners.add(new ClientRegistrationListener() {

            @Override
            public void onClientAdded(Client client) {
                System.out.println("New client added, " + client.getName() + " " + new Date() + " to Bank " + getId());
                clientMap.put(client.getName(), client);

            }
        });
    }

    public Bank(List<ClientRegistrationListener> registrationListeners) {
        this();
        this.listeners.addAll(registrationListeners);
    }

    public class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("Name: " + c.getGender().getGenderPrefix() + " " + c.getName());
            System.out.println("Overdraft: " + c.getInitialOverdraft());
            System.out.println("Balance:" + c.getBalance());
        }
    }


    public class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("Notification email for client " + c.getGender().getGenderPrefix() + " " + c.getName() + "to be sent");
        }
    }




    @Override
    public String toString () {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Map<String, Client> getClientMap() {
        return clientMap;
    }
}