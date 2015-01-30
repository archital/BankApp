package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.model.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by SCJP on 27.01.2015.
 */
public class ClientDAOImpl implements ClientDAO {
    @Override
    public Client findClientByName(Bank bank, String name) throws ClientNotFoundException, SQLException {


        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "SELECT ID, GENDER , TELEPHONE, EMAIL,  INITIAL_OVERDRAFT,  CITY " +
                "              FROM CLIENT  " +
                "              WHERE CLIENT_NAME = ?  AND BANK_ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        try {
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setInt(2, bank.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Client client = null;
        try {
            while (resultSet.next()) {

               client = new Client(name);

                Integer id = resultSet.getInt(1);
                client.setId(id);
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


                String sql2 = "SELECT acc.OVERDRAFT , acc.BALANCE , acc.ID " +
                        "                 FROM  ACCOUNT acc" +
                        "              WHERE acc.client_id= ?";

                PreparedStatement preparedStatement2 = null;
                try {
                    preparedStatement2 = conn.prepareStatement(sql2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    preparedStatement2.setInt(1, client.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                ResultSet resultSet2 = null;
                try {
                    resultSet2 = preparedStatement2.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Account account;
                try {
                    while (resultSet2.next()) {

                        float balance = resultSet2.getFloat(2);
                        float overdraft = resultSet2.getFloat(1);
                        Integer accId = resultSet2.getInt(3);


                        if (overdraft == 0){

                            account = new SavingAccount(balance, accId);

                            client.addAccount(account);
                            client.setActiveAccount(account);
                        } else if (overdraft != 0 ) {
                            account = new CheckingAccount(overdraft, balance, accId);
                            client.addAccount(account);
                            client.setActiveAccount(account);
                        }


                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        baseDAO.closeConnection();
        return client;
        }


    @Override
    public Client findClientById(Integer clientId) throws ClientNotFoundException, SQLException, ClientExistsException {


        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();



        String sql = "SELECT c.CLIENT_NAME, c.GENDER , c.TELEPHONE, " +
                "c.EMAIL,  c.INITIAL_OVERDRAFT,  c.CITY " +
                " FROM CLIENT c " +
                " WHERE c.ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, clientId);

        ResultSet resultSet = preparedStatement.executeQuery();
        Client client = new Client();
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





        baseDAO.closeConnection();
        return client;
    }

    @Override
    public Set<Client> getAllClients(Bank bank) throws SQLException {


        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();





        String sql = "SELECT  c.ID, c.CLIENT_NAME, c.GENDER , c.TELEPHONE, " +
                "c.EMAIL,  c.INITIAL_OVERDRAFT,  c.CITY  " +
                " FROM CLIENT c " +
                " WHERE c.BANK_ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, bank.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

       Set<Client> clients = new HashSet<Client>();

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
                    client.setActiveAccount(account);
                } else if (overdraft != 0) {
                    account = new CheckingAccount(overdraft, balance, accId);
                    client.addAccount(account);
                    client.setActiveAccount(account);
                }

            }

            clients.add(client);

        }

        bank.setClients(clients);

        baseDAO.closeConnection();
         return clients;

    }

    @Override
    public void save(Client client, Integer bankId) throws SQLException, DAOException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();


            if (client.getId() != null ) {
                String sql = "UPDATE CLIENT SET   BANK_ID = ? ,\n" +
                        " \tCLIENT_NAME= ?,\n" +
                        " \tGENDER = ?,\n" +
                        " \tTELEPHONE=  ?,\n" +
                        "  \tEMAIL = ?,\n" +
                        " \tINITIAL_OVERDRAFT = ?,\n" +
                        " \tCITY = ?" +
                        "where client.id = ?";

                PreparedStatement preparedStatement2 = conn.prepareStatement(sql);
                preparedStatement2.setInt(1, bankId);
                preparedStatement2.setString(2, client.getName());

                if (client.getGender().equals("m") || client.getGender().equals("M")) {
                    preparedStatement2.setString(3, "M");
                } else {
                    preparedStatement2.setString(3, "F");
                }

                preparedStatement2.setString(4, client.getTelephoneNumber());
                preparedStatement2.setString(5, client.getEmail());
                preparedStatement2.setFloat(6, client.getInitialOverdraft());
                preparedStatement2.setString(7, client.getCity());
                preparedStatement2.setInt(8, client.getId());

                preparedStatement2.executeUpdate();


                AccountDAO accountDAO = new AccountDAOImpl();
                accountDAO.save(client.getActiveAccount(), client);

            }


            if (client.getId() == null) {


                String sql4 = "INSERT INTO CLIENT(\n" +
                        " \tBANK_ID,  \tCLIENT_NAME,  \tGENDER,  \tTELEPHONE,  \tEMAIL," +
                        "  \tINITIAL_OVERDRAFT,  \tCITY )" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement2 = conn.prepareStatement(sql4);

                preparedStatement2.setInt(1, bankId);
                preparedStatement2.setString(2, client.getName());


                if (client.getGender().equals("m") || client.getGender().equals("M")) {
                    preparedStatement2.setString(3, "M");
                } else {
                    preparedStatement2.setString(3, "F");
                }

                preparedStatement2.setString(4, client.getTelephoneNumber());
                preparedStatement2.setNString(5, client.getEmail());
                preparedStatement2.setFloat(6, client.getInitialOverdraft());
                preparedStatement2.setNString(7, client.getCity());

                preparedStatement2.executeUpdate();

              ResultSet resultSet =  preparedStatement2.getGeneratedKeys();



                if ( resultSet == null || ! resultSet.next()) {
                    throw new DAOException("Impossible to save in DB. Can't get clientID.");
                }
                Integer clientId = resultSet.getInt(1);
                client.setId(clientId);

                AccountDAO accountDAO = new AccountDAOImpl();
                if(!(client.getAccounts().isEmpty())) {

                    accountDAO.save(client.getActiveAccount(), client);
                }
            }

        baseDAO.closeConnection();
        }




    @Override
    public void remove(Client client) throws SQLException {

        BaseDAO baseDAO = new BaseDAOImpl();
        Connection conn = baseDAO.openConnection();

        AccountDAO accountDAO = new AccountDAOImpl();
       accountDAO.removeByClientId(client.getId());

        String sql = "DELETE FROM CLIENT WHERE CLIENT.ID = ?";

        PreparedStatement preparedStatement3 = conn.prepareStatement(sql);
        preparedStatement3.setInt(1, client.getId());
        preparedStatement3.executeUpdate();



        baseDAO.closeConnection();
    }
}
