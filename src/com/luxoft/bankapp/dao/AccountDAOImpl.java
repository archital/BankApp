package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SCJP on 27.01.2015.
 */
public class AccountDAOImpl implements AccountDAO {

    private static AccountDAOImpl instance;

    private AccountDAOImpl() {
    }

    public static  AccountDAOImpl getInstance() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (((AbstractAccount) account).getId() != null) {
            String sql = "UPDATE ACCOUNT SET  BALANCE = ?, 	OVERDRAFT = ?, CLIENT_ID = ?" +
                    "where ACCOUNT.id = ?";

            PreparedStatement preparedStatement2 = null;
            try {
                preparedStatement2 = conn.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setFloat(1, account.getBalance());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (account instanceof CheckingAccount) {
                try {
                    preparedStatement2.setFloat(2, ((CheckingAccount) account).getOverdraft());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if (account instanceof  SavingAccount){
                try {
                    preparedStatement2.setNull(2, (Types.FLOAT));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                preparedStatement2.setInt(3, client.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setInt(4, ((AbstractAccount) account).getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } else  if (account instanceof CheckingAccount && ((CheckingAccount) account).getId() == null) {

            String sql = "INSERT INTO ACCOUNT(\n" +
                    "BALANCE, \tOVERDRAFT,  \tCLIENT_ID )" +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement3 = null;
            try {
                preparedStatement3 = conn.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement3.setFloat(1, account.getBalance());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement3.setFloat(2, ((CheckingAccount) account).getOverdraft());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement3.setInt(3, client.getId());
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
                    throw new DAOException("Impossible to save in DB. Can't get account ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DAOException e) {
                e.printStackTrace();
            }
            Integer accountId = null;
            try {
                accountId = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ((CheckingAccount) account).setId(accountId);


        }
        else if (account instanceof SavingAccount && ((SavingAccount) account).getId() == null) {

            String sql = "INSERT INTO ACCOUNT(\n" +
                    "BALANCE, \tOVERDRAFT,  \tCLIENT_ID )" +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement2 = null;
            try {
                preparedStatement2 = conn.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setFloat(1, account.getBalance());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setNull(2, Types.FLOAT);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.setInt(3, client.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement2.getGeneratedKeys();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                if ( resultSet == null || ! resultSet.next()) {
                    throw new DAOException("Impossible to save in DB. Can't get account ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DAOException e) {
                e.printStackTrace();
            }
            Integer accountId = null;
            try {
                accountId = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ((SavingAccount) account).setId(accountId);

        }

        baseDAO.closeConnection();
    }

    @Override
    public synchronized void removeByClientId(Integer id)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql = "DELETE FROM ACCOUNT WHERE CLIENT_ID = ?";

        PreparedStatement preparedStatement3 = null;
        try {
            preparedStatement3 = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement3.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        baseDAO.closeConnection();

    }

    @Override
    public synchronized void removeByClientName (String name)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DELETE\n" +
                " FROM ACCOUNT \n" +
                "WHERE CLIENT_ID = (SELECT ID FROM CLIENT WHERE  CLIENT_NAME = ? )";

        PreparedStatement preparedStatement3 = null;
        try {
            preparedStatement3 = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement3.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        baseDAO.closeConnection();
    }

    @Override
    public synchronized List<Account> getClientAccounts(Integer id) {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "SELECT BALANCE,  \tOVERDRAFT,  \tID  " +
                " FROM ACCOUNT " +
                " WHERE CLIENT_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Account> accounts = new ArrayList<Account>();

        try {
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
            e.printStackTrace();
        }
        baseDAO.closeConnection();
        return  accounts;

    }

    @Override
    public synchronized Account getAccountById (Integer id)  {

        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AbstractAccount account = null;



        String sql = "SELECT BALANCE,  \tOVERDRAFT, ID " +
                " FROM ACCOUNT " +
                " WHERE ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
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
            e.printStackTrace();
        }
        baseDAO.closeConnection();
        return  account;
    }

    @Override
    public synchronized void transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount)  {
        BaseDAO baseDAO = DAOFactory.getBaseDAO();
        Connection conn = null;
        try {
            conn = baseDAO.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - ?\n" +
                "WHERE ACCOUNT.ID = ? AND ACCOUNT.CLIENT_ID = ?";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setFloat(1, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            preparedStatement.setInt(2, accIdWithdraw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setInt(3, clIdWithdraw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql2 = "UPDATE ACCOUNT SET BALANCE = BALANCE + ?\n" +
                "WHERE ACCOUNT.ID = ? AND ACCOUNT.CLIENT_ID = ?";

        PreparedStatement preparedStatement2 = null;
        try {
            preparedStatement2 = conn.prepareStatement(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement2.setFloat(1, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement2.setInt(2, accIdDeposit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement2.setInt(3, clIdDeposit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        baseDAO.closeConnection();
    }

}
