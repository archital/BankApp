package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.model.Bank;

import java.sql.SQLException;

public interface BankService {

    Bank getBankByName(String name) throws SQLException;
    BankInfo getBankInfo(Bank bank) throws SQLException;
}
