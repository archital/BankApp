package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

/**
 * Created by SCJP on 03.02.2015.
 */
public class ServiceFactory {


    public static BankService getBankImpl() {
        return BankImpl.getInstance();
    }

    public static ClientService getClientImpl() {
        return ClientImpl.getInstance();
    }

    public static AccountService getAccountImpl() {
        return AccountImpl.getInstance();
    }

    public static BankFeedService getBankFeedService() {
        return BankFeedService.getInstance().getInstance();
    }
}
