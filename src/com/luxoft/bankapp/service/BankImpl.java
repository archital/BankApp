package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
<<<<<<< HEAD
import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.DAOException;
import com.luxoft.bankapp.model.Account;
=======
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
import com.luxoft.bankapp.model.Bank;

<<<<<<< HEAD
import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class BankImpl implements BankService {

    private static BankImpl instance;

    private BankImpl() {
    }

    public static  BankImpl getInstance() {
        if (instance == null) {
            instance = new  BankImpl();
        }
        return instance;
    }


    @Override
    public Bank getBankByName(String name) throws SQLException {

        BankDAO bankDAO = DAOFactory.getBankDAO();
      return   bankDAO.getBankByName(name);

    }

    @Override
    public BankInfo getBankInfo(Bank bank) throws SQLException {
        BankDAO bankDAO = DAOFactory.getBankDAO();

            BankInfo bankInfo =  bankDAO.getBankInfo(bank);
        return bankInfo;
    }

    @Override
    public void save(Bank bank) throws SQLException, DAOException {
        BankDAO bankDAO = DAOFactory.getBankDAO();

          bankDAO.save(bank);

=======
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
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
    }

}
