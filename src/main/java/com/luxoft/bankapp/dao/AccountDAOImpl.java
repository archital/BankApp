package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.*;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by SCJP on 27.01.2015.
 */
public class AccountDAOImpl implements AccountDAO {

    private static AccountDAOImpl instance;
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class.getName());

    private AccountDAOImpl() {
    }

    public static  AccountDAO getInstance() {
        if (instance == null) {
            instance = new  AccountDAOImpl();
        }
        return instance;
    }


    @Override
    public synchronized void save(Account account, Client client) {
        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();


        if (((AbstractAccount) account).getId() != null) {
            String sql = "UPDATE ACCOUNT SET  BALANCE = ?, 	OVERDRAFT = ?, CLIENT_ID = ?" +
                    "where ACCOUNT.id = ?";

            PreparedStatement preparedStatement2 = null;

                preparedStatement2 = conn.prepareStatement(sql);

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

        } else  if (account instanceof CheckingAccount && ((CheckingAccount) account).getId() == null) {

            String sql = "INSERT INTO ACCOUNT(\n" +
                    "BALANCE, \tOVERDRAFT,  \tCLIENT_ID )" +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement3 = null;

                preparedStatement3 = conn.prepareStatement(sql);
                preparedStatement3.setFloat(1, account.getBalance());
                preparedStatement3.setFloat(2, ((CheckingAccount) account).getOverdraft());
                preparedStatement3.setInt(3, client.getId());
                preparedStatement3.executeUpdate();

            ResultSet resultSet = preparedStatement3.getGeneratedKeys();

                if ( resultSet == null || ! resultSet.next()) {
                    logger.log(Level.SEVERE,"DAO Exception : Impossible to save in DB. Can't get account ID.");
                    throw new DAOException("Impossible to save in DB. Can't get account ID.");
                }

            Integer accountId = null;

                accountId = resultSet.getInt(1);

            ((CheckingAccount) account).setId(accountId);


        }
        else if (account instanceof SavingAccount && ((SavingAccount) account).getId() == null) {

            String sql = "INSERT INTO ACCOUNT(\n" +
                    "BALANCE, \tOVERDRAFT,  \tCLIENT_ID )" +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement2 = null;

                preparedStatement2 = conn.prepareStatement(sql);

                preparedStatement2.setFloat(1, account.getBalance());

                preparedStatement2.setNull(2, Types.FLOAT);

                preparedStatement2.setInt(3, client.getId());

                preparedStatement2.executeUpdate();

                ResultSet resultSet = null;

                resultSet = preparedStatement2.getGeneratedKeys();

                if ( resultSet == null || ! resultSet.next()) {
                    logger.log(Level.SEVERE,"DAO Exception : Impossible to save in DB. Can't get account ID.");
                    throw new DAOException("Impossible to save in DB. Can't get account ID.");

                }

            Integer accountId = resultSet.getInt(1);
            ((SavingAccount) account).setId(accountId);
        }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
            } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getMessage() + "DAO Exception  ", e);
            } finally {
            logger.log(Level.INFO, "Account  "+ ((AbstractAccount) account).getId()+" saved ");
            baseDAO.closeConnection();
        }


    }

    @Override
    public synchronized void removeByClientId(Integer id)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();


        String sql = "DELETE FROM ACCOUNT WHERE CLIENT_ID = ?";

        PreparedStatement preparedStatement3 = null;

            preparedStatement3 = conn.prepareStatement(sql);
            preparedStatement3.setInt(1, id);


            preparedStatement3.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        }
 finally {
            logger.log(Level.INFO, "Client with ID " +id +" accounts  were removed");
            baseDAO.closeConnection();
        }

    }

    @Override
    public synchronized void removeByClientName (String name) {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();

            String sql = "DELETE\n" +
                    " FROM ACCOUNT \n" +
                    "WHERE CLIENT_ID = (SELECT ID FROM CLIENT WHERE  CLIENT_NAME = ? )";

            PreparedStatement preparedStatement3 = null;

            preparedStatement3 = conn.prepareStatement(sql);

            preparedStatement3.setString(1, name);

            preparedStatement3.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        }
        finally {

            logger.log(Level.INFO, "Client with name " +name +" accounts  were removed");
            baseDAO.closeConnection();
        }
        }


    @Override
    public synchronized List<Account> getClientAccounts(Integer id) {


        List<Account> accounts = new ArrayList<Account>();
        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();

        String sql = "SELECT BALANCE,  \tOVERDRAFT,  \tID  " +
                " FROM ACCOUNT " +
                " WHERE CLIENT_ID = ?";

        PreparedStatement preparedStatement = null;

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

        ResultSet resultSet = null;

            resultSet = preparedStatement.executeQuery();

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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        }
        finally {
            logger.log(Level.INFO, "Client with ID " +id +" accounts  got from DB");

            baseDAO.closeConnection();
            return  accounts;
        }

    }

    @Override
    public synchronized Account getAccountById (Integer id)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        AbstractAccount account = null;
        try {
            conn = baseDAO.openConnection();
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {
            logger.log(Level.INFO, "Account with ID " +id +" got from DB");
            baseDAO.closeConnection();
            return account;
        }
    }

    @Override
    public synchronized void transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount)  {
        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();


        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - ?\n" +
                "WHERE ACCOUNT.ID = ? AND ACCOUNT.CLIENT_ID = ?";

        PreparedStatement preparedStatement = null;

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setInt(2, accIdWithdraw);


            preparedStatement.setInt(3, clIdWithdraw);

            preparedStatement.executeUpdate();

            conn.setAutoCommit(false);


        String sql2 = "UPDATE ACCOUNT SET BALANCE = BALANCE + ?\n" +
                "WHERE ACCOUNT.ID = ? AND ACCOUNT.CLIENT_ID = ?";

        PreparedStatement preparedStatement2 =conn.prepareStatement(sql2);

            preparedStatement2.setFloat(1, amount);
            preparedStatement2.setInt(2, accIdDeposit);
            preparedStatement2.setInt(3, clIdDeposit);

            preparedStatement2.executeUpdate();

            conn.commit();


            conn.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {
            logger.log(Level.INFO, " Transfer successful ");
            baseDAO.closeConnection();
        }
    }

}
