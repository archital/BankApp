package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.model.Bank;

import java.sql.SQLException;

public class BankImpl implements BankService {


    @Override
    public Bank getBankByName(String name) throws SQLException {

        BankDAO bankDAO = new BankDAOImpl();
      return   bankDAO.getBankByName(name);

    }

    @Override
    public BankInfo getBankInfo(Bank bank) throws SQLException {
        BankDAO bankDAO = new BankDAOImpl();

            BankInfo bankInfo =  bankDAO.getBankInfo(bank);
        return bankInfo;
    }

}
