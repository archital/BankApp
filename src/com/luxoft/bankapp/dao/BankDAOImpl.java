package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankInfo;


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

    private static BankDAOImpl instance;

    private BankDAOImpl() {
    }

    public static  BankDAOImpl getInstance() {
        if (instance == null) {
            instance = new  BankDAOImpl();
        }
        return instance;
    }

    Bank bank1;

    @Override
    public Bank getBankByName(String name)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql = "SELECT b.ID, b.NAME " +
                " FROM BANK b" +
                " WHERE b.NAME = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bank1 = new Bank();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                bank1.setId(id);
                String bankName = resultSet.getString(2);

                bank1.setName(bankName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        baseDAO.closeConnection();
        return bank1;
    }

    @Override
    public BankInfo getBankInfo(Bank bank)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int numberOfClients = 0;
        double totalAccountSum = 0;

        String sql2 = "SELECT count(c.ID),  SUM(acc.balance) \n" +
                "FROM BANK b LEFT JOIN CLIENT c\n" +
                "ON c.BANK_ID = b.ID\n" +
                " LEFT JOIN ACCOUNT acc ON\n" +
                "c.id = acc.CLIENT_ID\n" +
                " where b.NAME = ?";

        PreparedStatement preparedStatement2 = null;
        try {
            preparedStatement2 = conn.prepareStatement(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement2.setString(1, bank.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ResultSet resultSet2 = null;
        try {
            resultSet2 = preparedStatement2.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            while (resultSet2.next()) {


              numberOfClients = resultSet2.getInt(1);
              totalAccountSum = resultSet2.getFloat(2);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ClientDAO clientDAO = DAOFactory.getClientDAO();
        try {
            bank.setClients(clientDAO.getAllClients(bank));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BankInfo bankInfo = new BankInfo(bank);
        bankInfo.setNumberOfClients(numberOfClients);
        bankInfo.setTotalAccountSum(totalAccountSum);


        baseDAO.closeConnection();
        return bankInfo;

    }

    @Override
    public void save(Bank bank)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (bank.getId() != null) {
            String sql = "UPDATE BANK SET   NAME   = ? where id = ?";

            PreparedStatement preparedStatement2 = null;
            try {
                preparedStatement2 = conn.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setString(1, bank.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setInt(2, bank.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                preparedStatement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ClientDAO clientDAO = DAOFactory.getClientDAO();

            if(!(bank.getClients().isEmpty())) {
                for (Client c : bank.getClients()) {
                    try {
                        try {
                            clientDAO.save(c, bank.getId());
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        if (bank.getId() == null) {
            String sql3 = "INSERT INTO BANK(NAME)  VALUES (?)";

            PreparedStatement preparedStatement3 = null;
            try {
                preparedStatement3 = conn.prepareStatement(sql3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement3.setString(1, bank.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement3.getGeneratedKeys();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if ( resultSet == null || ! resultSet.next()) {
                    throw new DAOException("Impossible to save in DB. Can't get clientID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DAOException e) {
                e.printStackTrace();
            }
            Integer bankId = null;
            try {
                bankId = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            bank.setId(bankId);


            ClientDAO clientDAO = DAOFactory.getClientDAO();
            if(!(bank.getClients().isEmpty())) {


                for (Client c : bank.getClients()) {
                    try {
                        try {
                            clientDAO.save(c, bank.getId());
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


            baseDAO.closeConnection();
        }

    @Override
    public Bank load(String bankName) {



        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql = "SELECT  ID, NAME  FROM BANK WHERE NAME = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setString(1, bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bank bank = new Bank();

        try {
            while (resultSet.next()) {
                bank.setId(resultSet.getInt(1));
            bank.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ClientDAO clientDAO = DAOFactory.getClientDAO();
        Set<Client> clients = null;
        try {
            clients = clientDAO.getAllClients(bank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bank.setClients(clients);

        baseDAO.closeConnection();
        return bank;

    }
}

