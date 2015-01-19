package com.luxoft.bankapp;

import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;

import java.util.ArrayList;
import java.util.List;

import com.luxoft.bankapp.model.Bank.*;

/**
 * Created by SCJP on 14.01.2015.
 */
public class BankApplication {

    /**
     * @param args the command line arguments
     */
    private Bank bank;
    public static void main(String[] args) {
        BankApplication bankApplication = new BankApplication();
        bankApplication.initialize();


        System.out.println("Print Bank Report:");
        bankApplication.printBankReport();
        System.out.println("////////////////////////////////////");
        System.out.println("modify!");
        bankApplication.modifyBank();
        System.out.println("////////////////////////////////////");
        System.out.println("Print Bank Report:");
        bankApplication.printBankReport();
        System.out.println("////////////////////////////////////");



    }
    public  void initialize() {
        // create first client
        Account account1ForClient1 = new SavingAccount(10,100);
        Account account2ForClient1 = new CheckingAccount(2, 50);
        Client client1 = new Client();
        client1.setName("Peter");
        client1.setGender(Gender.MALE);
        client1.setActiveAccount(account1ForClient1);
        client1.setEmail("qqqq@gmail.com");
        client1.setTelephoneNumber("+380955422433");

        // create second client
        Account account1ForClient2 = new SavingAccount(3, 20);
        Account account2ForClient2 = new CheckingAccount(4, 35);
        Client client2 = new Client();
        client2.setName("Ludmila");
        client2.setGender(Gender.FEMALE);
        client2.setActiveAccount(account1ForClient2);
        client2.setEmail("qwef@gmail.com");
        client2.setTelephoneNumber("+3809523422436");


        List<ClientRegistrationListener> listeners = new ArrayList();

        bank = new Bank();
        bank = new Bank(listeners);
        bank.setBankNumber(1);
        BankService bankService = new BankImpl();
        try {
            bankService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addClient(bank, client2);
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
        System.out.println("///////////////Client1 toString////////////");
        System.out.println(client1.toString());
        System.out.println("///////////////Client2 toString////////////");
        System.out.println(client2.toString());
    }


    public  void modifyBank(){
        List<Client> clients = bank.getClients();
        for(Client client : clients){
            List<Account> accounts = client.getAccounts();
            for(Account account : accounts){
                account.deposit(150);

                try {
                    account.withdraw(60);
                } catch (NotEnoughFundsException e) {
                    e.printStackTrace();
                }

            }
        }
    };



    public void printBankReport() {


         bank.printReport();

    };


}