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


        String sql5 = "SELECT ID "+
                " FROM ACCOUNT " +
                "WHERE ID = ? ";


        PreparedStatement preparedStatement = conn.prepareStatement(sql5);

        if (account instanceof CheckingAccount) {

            preparedStatement.setInt(1, ((CheckingAccount) account).getId());
        }
    else if (account instanceof SavingAccount) {
            preparedStatement.setInt(1, ((SavingAccount) account).getId());
        }


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {


            if (account instanceof CheckingAccount && ((CheckingAccount) account).getId() == resultSet.getInt(1)) {
                String sql = "UPDATE ACCOUNT SET  BALANCE = ?, 	OVERDRAFT = ?, CLIENT_ID = ?" +
                        "where ACCOUNT.id = ?";

                PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
                preparedStatement2.setFloat(1, account.getBalance());
                preparedStatement2.setFloat(2, ((CheckingAccount) account).getOverdraft());
                preparedStatement2.setInt(3, client.getId());
                preparedStatement2.setInt(4, ((CheckingAccount) account).getId());
                preparedStatement2.executeUpdate();

                client.setActiveAccount(account);

            }

            if (account instanceof SavingAccount && ((SavingAccount) account).getId() == resultSet.getInt(1)) {
                String sql = "UPDATE ACCOUNT SET  BALANCE = ?, 	OVERDRAFT = ?, CLIENT_ID = ?" +
                        "where ACCOUNT.id = ?";

                PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
                preparedStatement2.setFloat(1, account.getBalance());
                preparedStatement2.setNull(2, Types.FLOAT);
                preparedStatement2.setInt(3, client.getId());
                preparedStatement2.setInt(4, ((SavingAccount) account).getId());
                preparedStatement2.executeUpdate();

                client.setActiveAccount(account);
            }



        }

        if(resultSet.wasNull()) {

            if (account instanceof CheckingAccount) {

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

            if (account instanceof SavingAccount) {

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
        }

        baseDAO.closeConnection();
    }

    @Override
    public void removeByClientId(int id) throws SQLException {

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
    public List<Account> getClientAccounts(int id) throws SQLException {

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

                AbstractAccount account = new CheckingAccount(overdraft, balance);
                account.setId(idAcc);
                accounts.add(account);
            }

            else if (overdraft == 0 ) {
                AbstractAccount account = new SavingAccount(balance);

                account.setId(idAcc);
                accounts.add(account);
            }

        }
        baseDAO.closeConnection();
        return  accounts;

    }

}
