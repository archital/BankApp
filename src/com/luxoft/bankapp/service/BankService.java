package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.expeption.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface BankService {

    Bank getBankByName(String name) throws SQLException;
    BankInfo getBankInfo(Bank bank) throws SQLException;
    public void  save(Bank bank) throws SQLException, ClientExistsException, DAOException;
}
