package com.luxoft.bankapp.test;

import com.luxoft.bankapp.annotation.annotation;
import com.luxoft.bankapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TestServiceTest {

    Bank bank1, bank2;

    @Before
    public void initBanks() throws Exception {


        bank1 = new Bank();
        bank1.setId(1);
        bank1.setName("bank");
        Client client1 = new Client();
        client1.setName("Ivan Ivanov");
        client1.setCity("Kiev");
        client1.setEmail("you@warning");
        client1.setGender(Gender.MALE);
        client1.setTelephoneNumber("0969876543");
        client1.setId(1);

        bank2 = new Bank();
        bank2.setId(2);
        bank2.setName("bank");
        Client client2 = new Client();
        client2.setName("Ivan Ivanov");
        client2.setCity("Kiev");
        client2.setEmail("you@warning");
        client2.setGender(Gender.MALE);
        client2.setTelephoneNumber("0969876543");
        client2.setId(2);


        Account account1 = new CheckingAccount(50, 100);
        Account account2 = new CheckingAccount(50, 100);


        client1.addAccount(account1);
        client2.addAccount(account2);


        Set<Client> clients = new HashSet();
        clients.add(client1);
      bank1.setClients(clients);

        clients.clear();
        clients.add(client2);
        bank2.setClients(clients);
    }

    @Test
    public void testIsEquals() throws Exception {
        assertTrue(TestService.isEquals(bank1, bank2));

    }
}