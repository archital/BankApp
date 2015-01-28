package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;

import java.io.Serializable;

/**
 * Created by SCJP on 14.01.2015.
 */
public abstract class AbstractAccount implements Account, Serializable {

    private int id;
    private float balance = 0;


    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }


    @Override
    public void deposit(float x) {
        Float b = getBalance() + x;
        balance = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (x > balance) {
            throw new NotEnoughFundsException();
        } else {
            Float b = balance - x;
            balance = b;
        }
    }


}
