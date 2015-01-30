package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exception.NotEnoughFundsException;

import java.io.Serializable;

/**
 * Created by SCJP on 14.01.2015.
 */
public abstract class AbstractAccount implements Account, Serializable {

    protected Integer id= null;
    protected float balance = 0;


    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }


    @Override
    public void deposit(float x) {
        balance = balance + x;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (x > balance) {
            throw new NotEnoughFundsException();
        } else {
            float b = balance - x;
            balance = b;
        }
    }


}
