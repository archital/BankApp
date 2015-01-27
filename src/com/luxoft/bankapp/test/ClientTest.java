package com.luxoft.bankapp.test;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class ClientTest {


    Client client;
    Account account;
    Set<Account> accounts;


    @Before
    public void setUp() throws Exception {
        client = new Client("Peter");
       accounts = new HashSet<Account>();
    }

    @Test
    public void testCreateAccountWithOnlyType() throws Exception {




      Account account1 =  client.createAccountWithOnlyType("s");
      Account account2 =  client.createAccountWithOnlyType("c");



        accounts.add(account1);
        accounts.add(account2);


        assertEquals(client.getAccounts(), accounts);


    }

    @Test
    public void testAddAccount() throws Exception {

        account = new SavingAccount();

        accounts.add(account);
        client.addAccount(account);

        assertEquals(client.getAccounts(), accounts);

    }
}