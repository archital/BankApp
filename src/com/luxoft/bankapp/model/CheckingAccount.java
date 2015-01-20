package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by SCJP on 14.01.2015.
 */
public class CheckingAccount extends AbstractAccount implements Serializable {


    private float overdraft = 0; // maximum size of acc


    public CheckingAccount(float overdraft, float balance) {
        this.overdraft = overdraft;
        setBalance(balance);
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
    public void withdraw(float x) throws OverDraftLimitExceededException{
        if (x > getBalance()) {
            throw new OverDraftLimitExceededException("OverDraftLimitExceededException", getOverdraft());
        } else {
        if(getBalance()> x){ // необходимое количество денег вычитается из значения overdraft.
            float NeedSum = getBalance() - x;
            float newOverdraft = getOverdraft() - NeedSum;
            setOverdraft(newOverdraft);
        }
        else {
            Float b = getBalance() - x;
            setBalance(b);
        }
        }
    }

    @Override
    public float decimalValue() {
        return 0;
    }

    @Override
    public void printReport() {
        System.out.print( "Checking Account" );
        System.out.println("Balance: " + getBalance()+ "} ");
    }

    @Override
    public String toString() {
        return "Checking Account{ " +
                "overdraft = " + overdraft +
                " Checking account Balance " + getBalance() +
                '}';
    }
}