package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.model.Bank;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SCJP on 27.01.2015.
 */
public class BankDAOImpl implements BankDAO {
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
        Bank bank1 = new Bank();

        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            bank1.setId(id);
            String bankName = resultSet.getString(2);

            bank1.setName(bankName);
        }


       baseDAO.closeConnection();
        return bank1;
    }
}