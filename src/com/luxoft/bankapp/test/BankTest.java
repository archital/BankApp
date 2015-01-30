package com.luxoft.bankapp.test;


import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import org.junit.Before;
import org.junit.Test;


import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;


public class BankTest extends Bank {

    Bank bank = new Bank();
    Set<Client> clients;
   Client client = new Client("Peter");
   Client client2 = new Client("Sasha");
   Client client3 = new Client("Vova");

 @Before
  public void testBeforeTest () throws Exception {



 }

    @Test
    public void testParseFeed() throws Exception {

    }

    @Test
    public void testAddClient() throws Exception {

         clients = new HashSet<Client>();
        clients.add(client);
        clients.add(client2);
        clients.add(client3);


        bank.addClient(client);
        bank.addClient(client2);
        bank.addClient(client3);

  assertEquals(bank.getClients(), clients);

    }

    @Test(expected=ClientExistsException.class)
    public void testAddClientExistsException() throws ClientExistsException {

        bank.addClient(client);
        bank.addClient(client2);
        bank.addClient(client3);

        Client client4 = new Client("Vova");
        bank.addClient(client4);


    }

    @Test
    public void testRemoveClient() throws Exception {

        clients = new HashSet<Client>();
        clients.add(client);
        clients.add(client2);


        bank.addClient(client);
        bank.addClient(client2);
        bank.addClient(client3);
        bank.removeClient(client3);



        assertEquals(bank.getClients(), clients);

    }

}