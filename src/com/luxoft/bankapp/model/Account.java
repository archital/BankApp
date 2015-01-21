package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;

import java.util.Map;

/**
 * Created by SCJP on 14.01.2015.
 */
public interface Account extends Report {

    public float getBalance();

    public void deposit(float x);

    public void withdraw(float x) throws NotEnoughFundsException;

    public void setBalance(float balance);

    float decimalValue();

    public void parseFeed(Map<String, String> feed);


}
