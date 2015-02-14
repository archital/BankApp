package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankImpl implements BankService {

    private static BankImpl instance;


    private BankImpl() {
    }

    public static  BankService getInstance() {
        if (instance == null) {
            instance = new  BankImpl();
        }
        return instance;
    }


    @Override
    public synchronized Bank getBankByName(String name) throws SQLException {


        BankDAO bankDAO = DAOFactory.getBankDAO();
      return   bankDAO.getBankByName(name);

    }

    @Override
    public synchronized BankInfo getBankInfo(Bank bank) throws SQLException {
        BankDAO bankDAO = DAOFactory.getBankDAO();

            BankInfo bankInfo =  bankDAO.getBankInfo(bank);
        return bankInfo;
    }

    @Override
    public synchronized void save(Bank bank) throws SQLException, DAOException {
        BankDAO bankDAO = DAOFactory.getBankDAO();

          bankDAO.save(bank);

    }

}
