package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by SCJP on 14.01.2015.
 */
public class SavingAccount extends AbstractAccount implements Serializable {


    public void parseFeed(Map<String, String> feed) {
        float overdraft = Float.parseFloat(feed.get("overdraft"));
        float balance = Float.parseFloat(feed.get("balance"));
        SavingAccount savingAccount  = new SavingAccount(balance);
    }

    public SavingAccount() {
    }

    public SavingAccount( float balance) {
        setBalance(balance);

    }

    @Override
    public void printReport() {
        System.out.print("Saving account ");
        System.out.println("Balance: " + getBalance()+ " }");
    }


    @Override
    public String toString() {
        return "Saving Account{ " +
                " Saving account Balance " + getBalance() +
                '}';
    }

    @Override
    public float decimalValue() {
        return 0;
    }
}
