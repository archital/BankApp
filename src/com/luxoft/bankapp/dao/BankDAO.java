package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.model.Bank;

import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface BankDAO {
  Bank getBankByName(String name) throws SQLException;
}
