package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.ClientService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by SCJP on 27.01.2015.
 */
public class ClientDAOImpl implements ClientDAO {


    private static final Logger logger = Logger.getLogger(ClientDAOImpl.class.getName());
    private static ClientDAOImpl instance;

    private ClientDAOImpl() {
    }

    public static  ClientDAO getInstance() {
        if (instance == null) {
            instance = new  ClientDAOImpl();
        }
        return instance;
    }

    @Override
    public synchronized Client findClientByName(Bank bank, String name)  {

        Client client = null;
        BaseDAO baseDAO = DAOFactory.getBaseDAO();

         Connection conn = null;
         try {
             conn = baseDAO.openConnection();

         String sql = "SELECT ID, GENDER , TELEPHONE, EMAIL,  INITIAL_OVERDRAFT,  CITY " +
                 "              FROM CLIENT  " +
                 "              WHERE CLIENT_NAME = ?  AND BANK_ID = ?";

         PreparedStatement preparedStatement = null;

                preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, name);

             preparedStatement.setInt(2, bank.getId());


         ResultSet resultSet = preparedStatement.executeQuery();



             while (resultSet.next()) {

                 client = new Client(name);

                 Integer id = resultSet.getInt(1);
                 client.setId(id);
                 String gender = resultSet.getString(2);
                 if (gender.equals("m") || gender.equals("M")) {

                     client.setGender(Gender.MALE);
                 }
                 if (gender.equals("f") || gender.equals("F")) {
                     client.setGender(Gender.FEMALE);
                 }

                 String telephone = resultSet.getString(3);
                 client.setTelephoneNumber(telephone);

                 String email = resultSet.getString(4);
                 client.setEmail(email);

                 float initialOverdraft = resultSet.getFloat(5);
                 client.setInitialOverdraft(initialOverdraft);

                 String city = resultSet.getString(6);
                 client.setCity(city);


                 String sql2 = "SELECT acc.OVERDRAFT , acc.BALANCE , acc.ID " +
                         "                 FROM  ACCOUNT acc" +
                         "              WHERE acc.client_id= ?";

                 PreparedStatement preparedStatement2 = null;

                     preparedStatement2 = conn.prepareStatement(sql2);


                     preparedStatement2.setInt(1, client.getId());

                 ResultSet resultSet2 = null;

                     resultSet2 = preparedStatement2.executeQuery();

                 Account account;
                     while (resultSet2.next()) {

                         float balance = resultSet2.getFloat(2);
                         float overdraft = resultSet2.getFloat(1);
                         Integer accId = resultSet2.getInt(3);


                         if (overdraft == 0) {

                             account = new SavingAccount(balance, accId);

                             client.addAccount(account);
                             client.setActiveAccount(account);
                         } else if (overdraft != 0) {
                             account = new CheckingAccount(overdraft, balance, accId);
                             client.addAccount(account);
                                 client.setActiveAccount(account);
                         }


                     }
                 }
             logger.log(Level.INFO, "Client found successful  " + client.toString());
             return client;
             } catch (SQLException e) {
             logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
    } finally {

        baseDAO.closeConnection();
             return client;
    }

        }


    @Override
    public synchronized Client findClientById(Integer clientId) throws ClientNotFoundException, ClientExistsException {

        Client client = new Client();
        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        String sql = "SELECT c.CLIENT_NAME, c.GENDER , c.TELEPHONE, " +
                "c.EMAIL,  c.INITIAL_OVERDRAFT,  c.CITY " +
                " FROM CLIENT c " +
                " WHERE c.ID = ?";

        PreparedStatement preparedStatement  = conn.prepareStatement(sql);
         preparedStatement.setInt(1, clientId);

        ResultSet resultSet = null;

            resultSet = preparedStatement.executeQuery();


        client.setId(clientId);

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                client.setName(name);
                String gender = resultSet.getString(2);
                if (gender.equals("m") || gender.equals("M")){

                    client.setGender(Gender.MALE);
                }
                if (gender.equals("f") || gender.equals("F")) {
                    client.setGender(Gender.FEMALE);
                }

                String telephone = resultSet.getString(3);
                client.setTelephoneNumber(telephone);

                String email = resultSet.getString(4);
                client.setEmail(email);

                float initialOverdraft = resultSet.getFloat(5);
                client.setInitialOverdraft(initialOverdraft);

                String city = resultSet.getString(6);
                client.setCity(city);


                String sql2 = "SELECT acc.OVERDRAFT , acc.BALANCE, acc.ID" +
                        "                 FROM ACCOUNT acc " +
                        "              WHERE acc.CLIENT_ID = ?";

                PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);

                preparedStatement2.setInt(1, client.getId());

                ResultSet resultSet2 = preparedStatement2.executeQuery();

                Account account;
                while (resultSet2.next()) {

                    float overdraft = resultSet2.getFloat(1);
                    float balance = resultSet2.getFloat(2);
                    Integer accId = resultSet2.getInt(3);


                    if (overdraft == 0) {

                        account = new SavingAccount(balance, accId);
                        client.addAccount(account);
                        client.setActiveAccount(account);
                    } else if (overdraft != 0) {
                        account = new CheckingAccount(overdraft, balance, accId);
                        client.addAccount(account);
                        client.setActiveAccount(account);
                    }

                }


            }
            logger.log(Level.INFO, "Client found successful  " + client.toString());
            return client;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {

            baseDAO.closeConnection();
            return client;
        }
    }

    @Override
    public synchronized Set<Client> getAllClients(Bank bank)  {

        Set<Client> clients = new HashSet<Client>();

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        String sql = "SELECT  c.ID, c.CLIENT_NAME, c.GENDER , c.TELEPHONE, " +
                "c.EMAIL,  c.INITIAL_OVERDRAFT,  c.CITY  " +
                " FROM CLIENT c " +
                " WHERE c.BANK_ID = ?";

        PreparedStatement preparedStatement = null;
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, bank.getId());


        ResultSet resultSet = null;
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();


                int clientId = resultSet.getInt(1);
                String clientName = resultSet.getString(2);
                String gender = resultSet.getString(3);
                String telephone = resultSet.getString(4);
                String email = resultSet.getString(5);
                float initialOverdraft = resultSet.getFloat(6);
                String city = resultSet.getString(7);


                client.setId(clientId);
                client.setName(clientName);

                if (gender.equals("m") || gender.equals("M")) {

                    client.setGender(Gender.MALE);
                }
                if (gender.equals("f") || gender.equals("F")) {
                    client.setGender(Gender.FEMALE);
                }

                client.setTelephoneNumber(telephone);
                client.setEmail(email);
                client.setInitialOverdraft(initialOverdraft);
                client.setCity(city);

                String sql4 = "SELECT OVERDRAFT , BALANCE, ID " +
                        "FROM ACCOUNT " +
                        "              WHERE CLIENT_ID = ?";

                PreparedStatement preparedStatement2 = conn.prepareStatement(sql4);

                preparedStatement2.setInt(1, client.getId());

                ResultSet resultSet2 = preparedStatement2.executeQuery();

                Account account;
                while (resultSet2.next()) {

                    float overdraft = resultSet2.getFloat(1);
                    float balance = resultSet2.getFloat(2);
                    Integer accId = resultSet2.getInt(3);

                    if (overdraft == 0) {

                        account = new SavingAccount(balance, accId);
                        client.addAccount(account);

                    } else if (overdraft != 0) {
                        account = new CheckingAccount(overdraft, balance, accId);
                        client.addAccount(account);

                    }

                }

                clients.add(client);

            }
            logger.log(Level.INFO, "Clients found successful  " + clients.toString());
            return  clients;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
        } finally {


            baseDAO.closeConnection();
            return  clients;
        }
 }

    @Override
    public synchronized void save(Client client, Integer bankId){

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        if (client.getId() != null ) {
                String sql = "UPDATE CLIENT SET   BANK_ID = ? ,\n" +
                        " \tCLIENT_NAME= ?,\n" +
                        " \tGENDER = ?,\n" +
                        " \tTELEPHONE=  ?,\n" +
                        "  \tEMAIL = ?,\n" +
                        " \tINITIAL_OVERDRAFT = ?,\n" +
                        " \tCITY = ?" +
                        "where client.id = ?";

            PreparedStatement preparedStatement2 = null;
                preparedStatement2 = conn.prepareStatement(sql);

                preparedStatement2.setInt(1, bankId);

                preparedStatement2.setString(2, client.getName());


            if (client.getGender() == Gender.MALE) {

                    preparedStatement2.setString(3, "M");

            } else if (client.getGender() == Gender.FEMALE) {

                    preparedStatement2.setString(3, "F");
            }


                preparedStatement2.setString(4, client.getTelephoneNumber());

                preparedStatement2.setString(5, client.getEmail());

                preparedStatement2.setFloat(6, client.getInitialOverdraft());

                preparedStatement2.setString(7, client.getCity());

                preparedStatement2.setInt(8, client.getId());

                preparedStatement2.executeUpdate();



            AccountDAO accountDAO = DAOFactory.getAccountDAO();


                if(!(client.getAccounts().isEmpty())) {
                    for (Account acc : client.getAccounts()) {
                            accountDAO.save(acc, client);

                    }
                }

            }


            if (client.getId() == null) {


                String sql4 = "INSERT INTO CLIENT(\n" +
                        " \tBANK_ID,  \tCLIENT_NAME,  \tGENDER,  \tTELEPHONE,  \tEMAIL," +
                        "  \tINITIAL_OVERDRAFT,  \tCITY )" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement2 = null;

                    preparedStatement2 = conn.prepareStatement(sql4);

                    preparedStatement2.setInt(1, bankId);

                    preparedStatement2.setString(2, client.getName());


                if (client.getGender() == Gender.MALE) {

                        preparedStatement2.setString(3, "M");

                } else if (client.getGender() == Gender.FEMALE) {

                        preparedStatement2.setString(3, "F");
                }
                    preparedStatement2.setString(4, client.getTelephoneNumber());
                    preparedStatement2.setNString(5, client.getEmail());
                    preparedStatement2.setFloat(6, client.getInitialOverdraft());
                    preparedStatement2.setNString(7, client.getCity());
                    preparedStatement2.executeUpdate();


                ResultSet resultSet = preparedStatement2.getGeneratedKeys();

                    if ( resultSet == null || ! resultSet.next()) {
                        logger.log(Level.SEVERE,"DAO Exception : Impossible to save in DB. Can't get client ID.");
                        throw new DAOException("Impossible to save in DB. Can't get clientID.");
                    }

                Integer clientId = null;

                    clientId = resultSet.getInt(1);

                client.setId(clientId);

                AccountDAO accountDAO = DAOFactory.getAccountDAO();
                if(!(client.getAccounts().isEmpty())) {
                    for (Account acc : client.getAccounts()) {

                            accountDAO.save(acc, client);

                    }
                }
            }
            logger.log(Level.INFO,  " Client saved successful ");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + " SQL Exception  ", e);
        } catch (DAOException e) {
            logger.log(Level.SEVERE, e.getMessage() + " DAO Exception  ", e);
        } finally {
            baseDAO.closeConnection();
        }

        }


    @Override
    public synchronized void remove(Client client)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        AccountDAO accountDAO = DAOFactory.getAccountDAO();

            accountDAO.removeByClientId(client.getId());


        String sql = "DELETE FROM CLIENT WHERE CLIENT.ID = ?";

        PreparedStatement preparedStatement3 = null;

            preparedStatement3 = conn.prepareStatement(sql);

            preparedStatement3.setInt(1, client.getId());

            preparedStatement3.executeUpdate();
            logger.log(Level.INFO,  " Client removed successful ");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + " SQL Exception  ", e);
        }

finally {
            baseDAO.closeConnection();
        }

    }
}
