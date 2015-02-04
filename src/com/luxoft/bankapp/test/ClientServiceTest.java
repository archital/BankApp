package com.luxoft.bankapp.test;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ClientServiceTest {


	Client client;
	AbstractAccount account;
	AbstractAccount account2;
	Bank bank;

	Set<Account> accountSet = new HashSet<Account>();
	Set<Client> clientSet = new HashSet<Client>();


	@Before
	public void setUp() throws Exception {

		accountSet.clear();
		clientSet.clear();
		account = new CheckingAccount(100, 200);
		account2 = new SavingAccount(200);


		accountSet.add(account);
		accountSet.add(account2);

		client = new Client();

		bank = new Bank();
		bank.setName("bank");

		client.setName("Ivan Ivanov");
		client.setCity("Kiev");
		client.setEmail("ewf@dsdf.com");
		client.setGender(Gender.MALE);
		client.setTelephoneNumber("0969876543");
		client.setInitialOverdraft(5000);

		client.setAccounts(accountSet);
		clientSet.add(client);
		bank.setClients(clientSet);
		ServiceFactory.getBankImpl().save(bank);
	}

	@Test
	public void testAddClient () throws Exception {

	Client client4 = new Client();
		client4.setName("Petr Petrov");
		client4.setCity("Kiev");
		client4.setEmail("ewf@dsdf.com");
		client4.setGender(Gender.MALE);
		client4.setTelephoneNumber("0900870003");
		client4.setInitialOverdraft(1000);

		ServiceFactory.getClientImpl().addClient(bank, client4);
		System.out.println("Add Client and get Clients");

		Set<Client> clients =  ServiceFactory.getClientImpl().getAllClients(bank);
		assertTrue(clients.contains(client4));
	}


	@Test(expected = ClientExistsException.class)
	public void testAddClientExists () throws Exception {

		Client client4 = new Client();
		client4.setName("Ivan Ivanov");
		client4.setCity("Kiev");
		client4.setEmail("ewf@dsdf.com");
		client4.setGender(Gender.MALE);
		client4.setTelephoneNumber("0900870003");
		client4.setInitialOverdraft(1000);

		System.out.println("Add Client and get Clients Exists Exception");
		ServiceFactory.getClientImpl().addClient(bank, client4);



	}

	@Test
	public void testRemoveClient () throws Exception {


 ServiceFactory.getClientImpl().removeClient(client, bank);
		Set<Client> clients = ServiceFactory.getClientImpl().getAllClients(bank);

		System.out.println("remove client");
		assertTrue(!clients.contains(client));

	}

	@Test
	public void testFindClientInDB () throws Exception {
	Client client1 = new Client();
		client1 = ServiceFactory.getClientImpl().findClientInDB(bank, client.getName());
		System.out.println("Find Client In DB");
		assertTrue(TestService.isEquals(client1, client));

	}
}