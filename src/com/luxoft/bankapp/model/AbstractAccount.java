package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;

/**
 * Created by SCJP on 14.01.2015.
 */
public abstract class AbstractAccount implements Account {

    private float balance = 0;



    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }



    @Override
    public void deposit(float x) { // добавляет значение  к балансу
        Float b = getBalance()+x;
        setBalance(b);
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {  // уменьшает баланс на x
        if (x > balance) {
            throw new NotEnoughFundsException("amount more then could have client", 100);
        } else {
            Float b = getBalance() - x;
            setBalance(b);
        }
    }


}
