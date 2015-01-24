package com.luxoft.bankapp.server;

import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.Gender;


import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 24.01.2015.
 */
public class AbstractServer {
	ServerSocket providerSocket;


	private Bank currentBank;


	public Bank getCurrentBank () {
		return currentBank;
	}


	public void initialize() {
		// create first client
		Account account1ForClient1 = new SavingAccount(100500);
		Account account2ForClient1 = new CheckingAccount(200, 500);
		Client client1 = new Client();
		client1.setName("Peter");
		client1.setGender(Gender.MALE);
		client1.setActiveAccount(account1ForClient1);
		client1.setEmail("qqqq@gmail.com");
		client1.setTelephoneNumber("+380955422433");
		client1.setCity("New York");

		// create second client
		Account account1ForClient2 = new SavingAccount(2056);
		Account account2ForClient2 = new CheckingAccount(423, 35100);
		Client client2 = new Client();
		client2.setName("Ludmila");
		client2.setGender(Gender.FEMALE);
		client2.setActiveAccount(account1ForClient2);
		client2.setEmail("qwef@gmail.com");
		client2.setTelephoneNumber("+3809523422436");
		client2.setCity("Dnepr");


		List<ClientRegistrationListener> listeners = new ArrayList();

		currentBank = new Bank();
		currentBank = new Bank(listeners);
		currentBank.setBankNumber(1);
		BankService bankService = new BankImpl();
		try {
			bankService.addClient( currentBank, client1);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		try {
			bankService.addClient( currentBank, client2);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		try {
			bankService.addAccount(client1, account1ForClient1);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		try {
			bankService.addAccount(client1, account2ForClient2);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		try {
			bankService.addAccount(client2, account1ForClient2);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		try {
			bankService.addAccount(client2, account2ForClient1);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		System.out.println("///////////////Client 1 toString////////////");
		System.out.println(client1.toString());

		System.out.println("///////////////Client 2 toString////////////");
		System.out.println(client2.toString());


	}





}
