package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;

/**
 * Created by SCJP on 14.01.2015.
 */
public class SavingAccount extends AbstractAccount {

    public float minoverdraft = 0;
    private  String accountType = "Saving account";

    public float getMinoverdraft() {
        return minoverdraft;
    }

    public void setMinoverdraft(float minoverdraft) {
        this.minoverdraft = minoverdraft;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public SavingAccount() {
    }

    public SavingAccount(float minoverdraft, float balance) {
        this.minoverdraft = minoverdraft;
        setBalance(balance);
        deposit(minoverdraft);
        if(getBalance()>= minoverdraft) { //уменьшающий баланс на minoverdraft в случае, если баланс >= minoverdraft
            try {
                withdraw(minoverdraft);
            } catch (NotEnoughFundsException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void printReport() {
        System.out.print(getAccountType());
        System.out.println("Balance: " + getBalance()+ " }");
    }


    @Override
    public String toString() {
        return "Saving Account{ " +
                " minoverdraft = " + minoverdraft +
                " Saving account Balance " + getBalance() +
                '}';
    }

    @Override
    public float decimalValue() {
        return 0;
    }
}
