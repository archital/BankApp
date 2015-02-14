package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.model.Account;

/**
 * Created by SCJP on 03.02.2015.
 */
public class DAOFactory {

    public static BaseDAO getBaseDAO() {
        return BaseDAOImpl.getInstance();
    }

    public static BankDAO getBankDAO() {
        return BankDAOImpl.getInstance();
    }


    public static ClientDAO getClientDAO() {
        return ClientDAOImpl.getInstance();
    }

    public static AccountDAO getAccountDAO() {
        return AccountDAOImpl.getInstance();
    }

}
