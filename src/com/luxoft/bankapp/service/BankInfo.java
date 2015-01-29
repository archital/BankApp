package com.luxoft.bankapp.service;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;
import com.sun.deploy.util.SessionState;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by acer on 20.01.2015.
 */
public class BankInfo {


   private int numberOfClients;
   private double totalAccountSum;
    Map<String, List<Client>> clientsByCity;
    private Set<Client> clientsSorted;

    public Set<Client> getClientsSorted () {
        return clientsSorted;
    }


    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public double getTotalAccountSum() {
        return totalAccountSum;
    }

    public void setTotalAccountSum(double totalAccountSum) {
        this.totalAccountSum = totalAccountSum;
    }

    public Map<String, List<Client>> getClientsByCity() {
        return clientsByCity;
    }



    public BankInfo() {
    }

    public BankInfo(Bank bank) {

        BankReport bankReport = new BankReport();
     numberOfClients = (int) bankReport.getNumberOfClients(bank);
     totalAccountSum = bankReport.getAccountsNumber(bank);
        clientsByCity = bankReport.getClientsByCity(bank);
        clientsSorted = bankReport.getClientsSorted(bank);


    }

    @Override
    public String toString () {
        return "BankInfo{" +
                "numberOfClients= " + numberOfClients +
                ", totalAccountSum= " + totalAccountSum +
                ", clientsByCity= " + clientsByCity +
                ", clientsSorted= " + clientsSorted +
                '}';
    }
}
