package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exception.OverDraftLimitExceededException;

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
                throw new OverDraftLimitExceededException(x, this);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

       CheckingAccount checkingAccount = (CheckingAccount) o;
        if (Float.compare(checkingAccount.balance, balance) != 0) return false;
        if (Float.compare(checkingAccount.overdraft, overdraft) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
        result = 31 * result + (overdraft != +0.0f ? Float.floatToIntBits(overdraft) : 0);
        return result;
    }


}