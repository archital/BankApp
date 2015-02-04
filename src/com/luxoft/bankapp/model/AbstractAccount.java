package com.luxoft.bankapp.model;

<<<<<<< HEAD
import com.luxoft.bankapp.annotation.annotation;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
=======
import com.luxoft.bankapp.exception.NotEnoughFundsException;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

import java.io.Serializable;

/**
 * Created by SCJP on 14.01.2015.
 */
public abstract class AbstractAccount implements Account, Serializable {

<<<<<<< HEAD
    @annotation.NoDB    protected Integer id= null;
=======
    protected Integer id= null;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAccount abstractAccount = (AbstractAccount) o;
        if (Float.compare(abstractAccount.balance, balance) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
        return result;
    }


}
