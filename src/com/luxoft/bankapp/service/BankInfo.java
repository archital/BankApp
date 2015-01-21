package com.luxoft.bankapp.service;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;
import com.sun.deploy.util.SessionState;

/**
 * Created by acer on 20.01.2015.
 */
public class BankInfo {


    public BankInfo() {
    }

    public BankInfo(Bank bank) {
        BankReport bankReport = new BankReport();
        System.out.println("Number of clients : ");
        bankReport.getNumberOfClients(bank);
        System.out.println("Number of accounts : ");
        bankReport.getAccountsNumber(bank);
        System.out.println("Total sum that client's get more than limit is: ");
        bankReport.getBankCreditSum(bank);

    }

    public BankInfo(Bank bank, Client client) throws ClientExistsException {
        BankImpl bank1 = new BankImpl();
        bank1.addClient(bank, client);
        bank1.removeClient(bank, client);

    }


}
