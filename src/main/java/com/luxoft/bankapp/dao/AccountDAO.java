package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exception.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface AccountDAO {
    public void save(Account account, Client client) throws SQLException, DAOException;
    public void removeByClientId(Integer id) throws SQLException;
    public void removeByClientName(String name) throws SQLException;
    public List<Account> getClientAccounts(Integer id) throws SQLException;
    public Account getAccountById(Integer id) throws SQLException;
    public void transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount) throws SQLException;
}
