package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankInfo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by SCJP on 27.01.2015.
 */
public class BankDAOImpl implements BankDAO {

    private static final Logger logger = Logger.getLogger(BankDAOImpl.class.getName());

    private static BankDAOImpl instance;

    private BankDAOImpl() {
    }

    public static  BankDAO getInstance() {
        if (instance == null) {
            instance = new  BankDAOImpl();
        }
        return instance;
    }

  private   Bank bank1;


    @Override
    public synchronized Bank getBankByName(String name)  {


        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        synchronized (baseDAO) {
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();

        String sql = "SELECT b.ID, b.NAME " +
                " FROM BANK b" +
                " WHERE b.NAME = ?";



        PreparedStatement preparedStatement = null;

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, name);



        ResultSet resultSet = null;

            resultSet = preparedStatement.executeQuery();

        bank1 = new Bank();

                while (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    bank1.setId(id);
                    String bankName = resultSet.getString(2);

                    bank1.setName(bankName);
                }
            logger.log(Level.INFO, "Bank  was found"+ bank1.toString());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {
            baseDAO.closeConnection();
        }
        }
        return bank1;

    }

    @Override
    public synchronized BankInfo getBankInfo(Bank bank)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();

        int numberOfClients = 0;
        double totalAccountSum = 0;

        String sql2 = "SELECT count(c.ID),  SUM(acc.balance) \n" +
                "FROM BANK b LEFT JOIN CLIENT c\n" +
                "ON c.BANK_ID = b.ID\n" +
                " LEFT JOIN ACCOUNT acc ON\n" +
                "c.id = acc.CLIENT_ID\n" +
                " where b.NAME = ?";

        PreparedStatement preparedStatement2 = null;

            preparedStatement2 = conn.prepareStatement(sql2);

            preparedStatement2.setString(1, bank.getName());

        ResultSet resultSet2 = null;

            resultSet2 = preparedStatement2.executeQuery();

            while (resultSet2.next()) {


              numberOfClients = resultSet2.getInt(1);
              totalAccountSum = resultSet2.getFloat(2);

            }


        ClientDAO clientDAO = DAOFactory.getClientDAO();

            bank.setClients(clientDAO.getAllClients(bank));

        BankInfo bankInfo = new BankInfo(bank);
        bankInfo.setNumberOfClients(numberOfClients);
        bankInfo.setTotalAccountSum(totalAccountSum);
            logger.log(Level.INFO, "Shows bankInfo successful");
            return bankInfo;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {
            baseDAO.closeConnection();
        }
        return null;

    }

    @Override
    public synchronized void save(Bank bank)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();

        if (bank.getId() != null) {
            String sql = "UPDATE BANK SET   NAME   = ? where id = ?";

            PreparedStatement preparedStatement2 = null;

                preparedStatement2 = conn.prepareStatement(sql);

                preparedStatement2.setString(1, bank.getName());

                preparedStatement2.setInt(2, bank.getId());

                preparedStatement2.executeUpdate();

            ClientDAO clientDAO = DAOFactory.getClientDAO();

            if(!(bank.getClients().isEmpty())) {
                for (Client c : bank.getClients()) {
                    try {
                        clientDAO.save(c, bank.getId());
                    } catch (DAOException e) {
                        logger.log(Level.SEVERE, e.getMessage() + "DAO Exception when try to save client ", e);
                    }
                }
                    }
                }




        if (bank.getId() == null) {
            String sql3 = "INSERT INTO BANK(NAME)  VALUES (?)";

            PreparedStatement preparedStatement3 = null;

                preparedStatement3 = conn.prepareStatement(sql3);


                preparedStatement3.setString(1, bank.getName());


                preparedStatement3.executeUpdate();


            ResultSet resultSet = null;

                resultSet = preparedStatement3.getGeneratedKeys();


                if ( resultSet == null || ! resultSet.next()) {
                    try {
                        logger.log(Level.SEVERE,"DAO Exception : Impossible to save in DB. Can't get client ID.");
                        throw new DAOException("Impossible to save in DB. Can't get client ID.");
                    } catch (DAOException e) {
                        logger.log(Level.SEVERE, e.getMessage() + "DAO Exception when try to find client ID in DB ", e);
                    }
                }
            Integer bankId = null;

                bankId = resultSet.getInt(1);

            bank.setId(bankId);


            ClientDAO clientDAO = DAOFactory.getClientDAO();
            if(!(bank.getClients().isEmpty())) {


                for (Client c : bank.getClients()) {

                    try {
                        clientDAO.save(c, bank.getId());
                    } catch (DAOException e) {
                        logger.log(Level.SEVERE, "DAO Exception : Impossible to save in DB. Can't get client ID.");
                    }

                }
            }

        }
            logger.log(Level.INFO, "Bank saved successful "+ bank.toString());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {
            baseDAO.closeConnection();
        }
    }

    @Override
    public synchronized Bank load(String bankName) {



        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        String sql = "SELECT  ID, NAME  FROM BANK WHERE NAME = ?";

        PreparedStatement preparedStatement = null;

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, bankName);


        ResultSet resultSet = null;

            resultSet = preparedStatement.executeQuery();

        Bank bank = new Bank();

            while (resultSet.next()) {
                bank.setId(resultSet.getInt(1));
            bank.setName(resultSet.getString(2));
            }

        ClientDAO clientDAO = DAOFactory.getClientDAO();
        Set<Client> clients = null;

            clients = clientDAO.getAllClients(bank);

        bank.setClients(clients);
            logger.log(Level.INFO, "Bank loaded successful "+ bank.toString());
return bank;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {
            baseDAO.closeConnection();
        }
        return null;

    }
}

