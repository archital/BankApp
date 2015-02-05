package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankInfo;

import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface BankDAO {
  Bank getBankByName(String name) throws SQLException;
    BankInfo getBankInfo(Bank bank) throws SQLException;
    void save(Bank bank) throws SQLException, DAOException;
    Bank load (String bankName) throws SQLException;
}
