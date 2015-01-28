package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SCJP on 27.01.2015.
 */
public interface AccountDAO {
    public void save(Account account,Client client) throws SQLException;
    public void removeByClientId(int id) throws SQLException;
    public List<Account> getClientAccounts(int id) throws SQLException;
}
