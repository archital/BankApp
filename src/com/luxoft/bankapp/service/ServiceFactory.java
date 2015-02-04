package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

/**
 * Created by SCJP on 03.02.2015.
 */
public class ServiceFactory {


    public static BankImpl getBankImpl() {
        return BankImpl.getInstance();
    }

    public static ClientImpl getClientImpl() {
        return ClientImpl.getInstance();
    }

    public static AccountImpl getAccountImpl() {
        return AccountImpl.getInstance();
    }
}
