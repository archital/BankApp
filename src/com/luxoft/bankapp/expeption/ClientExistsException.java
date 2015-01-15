package com.luxoft.bankapp.expeption;

/**
 * Created by SCJP on 15.01.2015.
 */
public class ClientExistsException extends BankException {
    public ClientExistsException(String s) {
        super(s);
    }
}
