package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.exception.OverDraftLimitExceededException;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SCJP on 29.01.2015.
 */
public interface AccountService {

    public void addAccount(Client client, Account account) throws SQLException;
    public List<Account> getClientAccounts (Integer id) throws SQLException;
     public void setActiveAccount(Client client, Account account);
    public void deposit(float x, Account account);
    public void withdraw(float x, Account account) throws NotEnoughFundsException, OverDraftLimitExceededException, NotEnoughFundsException;
    public float decimalValue(Account account);
    public Account getAccountById(Integer id);
    public void Transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount ) throws NotEnoughFundsException, OverDraftLimitExceededException;;

}
