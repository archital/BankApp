package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;

public interface BankService {

   public Bank getBankByName(String name) throws SQLException;
   public BankInfo getBankInfo(Bank bank) throws SQLException;
    public void  save(Bank bank) throws SQLException, ClientExistsException, DAOException, DAOException;
}
