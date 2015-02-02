package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankInfo;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by SCJP on 27.01.2015.
 */
public class BankDAOImpl implements BankDAO {


    Bank bank1;

    @Override
    public Bank getBankByName(String name) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();


        String sql = "SELECT b.ID, b.NAME " +
                " FROM BANK b" +
                " WHERE b.NAME = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);


        ResultSet resultSet = preparedStatement.executeQuery();
         bank1 = new Bank();

        while (resultSet.next()) {
            Integer id = resultSet.getInt(1);
            bank1.setId(id);
            String bankName = resultSet.getString(2);

            bank1.setName(bankName);
        }

       baseDAO.closeConnection();
        return bank1;
    }

    @Override
    public BankInfo getBankInfo(Bank bank) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

        int numberOfClients = 0;
        double totalAccountSum = 0;

        String sql2 = "SELECT count(c.ID),  SUM(acc.balance) \n" +
                "FROM BANK b LEFT JOIN CLIENT c\n" +
                "ON c.BANK_ID = b.ID\n" +
                " LEFT JOIN ACCOUNT acc ON\n" +
                "c.id = acc.CLIENT_ID\n" +
                " where b.NAME = ?";

        PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
        preparedStatement2.setString(1, bank.getName());


        ResultSet resultSet2 = preparedStatement2.executeQuery();


        while (resultSet2.next()) {


          numberOfClients = resultSet2.getInt(1);
          totalAccountSum = resultSet2.getFloat(2);

        }

        ClientDAO clientDAO = new ClientDAOImpl();
        bank.setClients(clientDAO.getAllClients(bank));

        BankInfo bankInfo = new BankInfo(bank);
        bankInfo.setNumberOfClients(numberOfClients);
        bankInfo.setTotalAccountSum(totalAccountSum);


        baseDAO.closeConnection();
        return bankInfo;

    }

}