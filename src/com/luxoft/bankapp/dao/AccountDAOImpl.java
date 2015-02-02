package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.model.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SCJP on 27.01.2015.
 */
public class AccountDAOImpl implements AccountDAO {
    @Override
    public void save(Account account, Client client) throws SQLException {
        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

        if (((AbstractAccount) account).getId() != null) {
            String sql = "UPDATE ACCOUNT SET  BALANCE = ?, 	OVERDRAFT = ?, CLIENT_ID = ?" +
                    "where ACCOUNT.id = ?";

            PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
            preparedStatement2.setFloat(1, account.getBalance());

            if (account instanceof CheckingAccount) {
            preparedStatement2.setFloat(2, ((CheckingAccount) account).getOverdraft());

            }

            if (account instanceof  SavingAccount){
                preparedStatement2.setNull(2, (Types.FLOAT));
            }
            preparedStatement2.setInt(3, client.getId());
            preparedStatement2.setInt(4, ((AbstractAccount) account).getId());
            preparedStatement2.executeUpdate();

            client.setActiveAccount(account);

        } else  if (account instanceof CheckingAccount && ((CheckingAccount) account).getId() == null) {

            String sql = "INSERT INTO ACCOUNT(\n" +
                    "BALANCE, \tOVERDRAFT,  \tCLIENT_ID )" +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement3 = conn.prepareStatement(sql);
            preparedStatement3.setFloat(1, account.getBalance());
            preparedStatement3.setFloat(2, ((CheckingAccount) account).getOverdraft());
            preparedStatement3.setInt(3, client.getId());
            preparedStatement3.executeUpdate();

            client.setActiveAccount(account);
        }
        else if (account instanceof SavingAccount && ((SavingAccount) account).getId() == null) {

            String sql = "INSERT INTO ACCOUNT(\n" +
                    "BALANCE, \tOVERDRAFT,  \tCLIENT_ID )" +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
            preparedStatement2.setFloat(1, account.getBalance());
            preparedStatement2.setNull(2, Types.FLOAT);
            preparedStatement2.setInt(3, client.getId());
            preparedStatement2.executeUpdate();

            client.setActiveAccount(account);

        }

        baseDAO.closeConnection();
    }

    @Override
    public void removeByClientId(Integer id) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql = "DELETE FROM ACCOUNT WHERE CLIENT_ID = ?";

        PreparedStatement preparedStatement3 = conn.prepareStatement(sql);

        preparedStatement3.setInt(1, id);
        preparedStatement3.executeUpdate();


        baseDAO.closeConnection();

    }

    @Override
    public void removeByClientName (String name) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DELETE\n" +
                " FROM ACCOUNT \n" +
                "WHERE CLIENT_ID = (SELECT ID FROM CLIENT WHERE  CLIENT_NAME = ? )";

        PreparedStatement preparedStatement3 = conn.prepareStatement(sql);

        preparedStatement3.setString(1, name);
        preparedStatement3.executeUpdate();


        baseDAO.closeConnection();
    }

    @Override
    public List<Account> getClientAccounts(Integer id) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

        String sql = "SELECT BALANCE,  \tOVERDRAFT,  \tID  " +
                " FROM ACCOUNT " +
                " WHERE CLIENT_ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Account> accounts = new ArrayList<Account>();

        while (resultSet.next()) {

        float balance = resultSet.getFloat(1);
        float overdraft =    resultSet.getFloat(2);
            int idAcc  = resultSet.getInt(3);

            if (overdraft != 0){

               Account account = new CheckingAccount(overdraft, balance, idAcc);
                accounts.add(account);
            }

            else if (overdraft == 0 ) {
             Account account = new SavingAccount(balance, idAcc);
                accounts.add(account);
            }

        }
        baseDAO.closeConnection();
        return  accounts;

    }

    @Override
    public Account getAccountById (Integer id) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

       AbstractAccount account = null;



        String sql = "SELECT BALANCE,  \tOVERDRAFT, ID " +
                " FROM ACCOUNT " +
                " WHERE ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {

            float balance = resultSet.getFloat(1);
            float overdraft =  resultSet.getFloat(2);
           Integer accId =  resultSet.getInt(3);

            if (overdraft != 0){

                account = new CheckingAccount(overdraft, balance, accId);
            }

            else if (overdraft == 0 ) {
                 account = new SavingAccount(balance, accId);
            }

        }
        baseDAO.closeConnection();
        return  account;
    }

    @Override
    public void transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount) throws SQLException {
        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - ?\n" +
                "WHERE ACCOUNT.ID = ? AND ACCOUNT.CLIENT_ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setFloat(1, amount);

        preparedStatement.setInt(2, accIdWithdraw);
        preparedStatement.setInt(3, clIdWithdraw);
         preparedStatement.executeUpdate();
        conn.setAutoCommit(false);

        String sql2 = "UPDATE ACCOUNT SET BALANCE = BALANCE + ?\n" +
                "WHERE ACCOUNT.ID = ? AND ACCOUNT.CLIENT_ID = ?";

        PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
        preparedStatement2.setFloat(1, amount);
        preparedStatement2.setInt(2, accIdDeposit);
        preparedStatement2.setInt(3, clIdDeposit);
        preparedStatement2.executeUpdate();


           conn.commit();
           conn.setAutoCommit(true);

            baseDAO.closeConnection();
    }

}
