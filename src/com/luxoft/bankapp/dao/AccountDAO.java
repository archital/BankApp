package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.expeption.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface AccountDAO {
<<<<<<< HEAD
    public void save(Account account,Client client) throws SQLException, DAOException;
=======
    public void save(Account account,Client client) throws SQLException;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
    public void removeByClientId(Integer id) throws SQLException;
    public void removeByClientName(String name) throws SQLException;
    public List<Account> getClientAccounts(Integer id) throws SQLException;
    public Account getAccountById(Integer id) throws SQLException;
    public void transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount ) throws SQLException;
}
