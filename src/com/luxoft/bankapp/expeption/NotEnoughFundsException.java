package com.luxoft.bankapp.expeption;

/**
 * Created by SCJP on 15.01.2015.
 */
public class NotEnoughFundsException extends BankException {
    private float amount;

    public NotEnoughFundsException(String s, float amount) {
        super(s);
        this.amount = amount;

    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
