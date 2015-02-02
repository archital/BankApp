package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by SCJP on 14.01.2015.
 */
public class CheckingAccount extends AbstractAccount implements Serializable {


    private float overdraft = 0;



    public CheckingAccount(float overdraft, float balance) {
        this.overdraft = overdraft;
        this.balance = balance;
    }


    public CheckingAccount(float overdraft, float balance, Integer id) {
        this.overdraft = overdraft;
        this.balance = balance;
        this.id = id;
    }

    public void parseFeed(Map<String, String> feed) {
        float overdraft = Float.parseFloat(feed.get("overdraft"));
        float balance = Float.parseFloat(feed.get("balance"));
        CheckingAccount checkingAccount = new CheckingAccount(overdraft, balance);

    }



    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public float getOverdraft() {
        return overdraft;
    }


    public CheckingAccount() {
    }



    @Override
    public void withdraw(float x) throws OverDraftLimitExceededException {
        if (x > getBalance()) {

            overdraft = overdraft - (x - getBalance());
            if(overdraft < 0 ){
                throw new OverDraftLimitExceededException(x);
            }

        } else if ((getBalance() + getOverdraft()) >= x) {
            float newBalance = getBalance()  - x;
            setBalance(newBalance);
        }
    }

    @Override
    public float decimalValue() {

       float res = (float) (Math.rint(100.0 * balance) / 100.0);

        return res;
    }

    @Override
    public void printReport() {
        System.out.print("Checking Account");
        System.out.println("Balance: " + balance + "} ");
    }


    @Override
    public String toString() {
        return "Checking Account{ " +
                " ID " + id +
                " overdraft = " + overdraft +
                " Checking account Balance " + balance +
                '}';
    }
}