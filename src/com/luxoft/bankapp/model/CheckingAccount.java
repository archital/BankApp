package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;

/**
 * Created by SCJP on 14.01.2015.
 */
public class CheckingAccount extends AbstractAccount {


    private float overdraft = 0; // maximum size of acc

    public CheckingAccount(float overdraft, float balance) {
        this.overdraft = overdraft;
        setBalance(balance);
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void withdraw(float x) throws OverDraftLimitExceededException{
        if (x > getBalance()) {
            throw new OverDraftLimitExceededException("OverDraftLimitExceededException", 100);
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
        System.out.print("Checking account { ");
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