package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exception.NotEnoughFundsException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by SCJP on 14.01.2015.
 */
public class SavingAccount extends AbstractAccount implements Serializable {




    public void parseFeed(Map<String, String> feed) {
        float overdraft = Float.parseFloat(feed.get("overdraft"));
        float balance = Float.parseFloat(feed.get("balance"));
        SavingAccount savingAccount = new SavingAccount(balance);
    }

    public SavingAccount() {
    }

    public SavingAccount(float balance) {
      this.balance = balance;

    }

    public SavingAccount(float balance, Integer id) {
        this.balance = balance;
        this.id = id;

    }




    @Override
    public void printReport() {
        System.out.print("Saving account ");
        System.out.println("Balance: " + balance + " }");
    }


    @Override
    public String toString() {
        return "Saving Account{ " +
                " ID " + id +
                " Saving account Balance " + balance +
                '}';
    }

    @Override
    public float decimalValue() {
        float res = (float) (Math.rint(100.0 * balance) / 100.0);

        return res;
    }
}

