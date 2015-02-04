package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.exception.*;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by SCJP on 29.01.2015.
 */
public class AccountImpl implements  AccountService{

    private static AccountImpl instance;

    private AccountImpl() {
    }

    public static  AccountImpl getInstance() {
        if (instance == null) {
            instance = new AccountImpl();
        }
        return instance;
    }

    @Override
    public void addAccount(Client client, Account account) throws  SQLException {
        AccountDAO accountDAO = DAOFactory.getAccountDAO();

        accountDAO.save(account, client);

    }

    @Override
    public List<Account> getClientAccounts(Integer id) throws SQLException {
        AccountDAO accountDAO = DAOFactory.getAccountDAO();

        List<Account> accounts = accountDAO.getClientAccounts(id);

        return  accounts;
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public void deposit(float x, Account account) {
        account.setBalance(account.getBalance()+ x);
    }

    @Override
    public void withdraw(float x , Account account) throws NotEnoughFundsException,  OverDraftLimitExceededException{

        if(account instanceof SavingAccount) {
        if (x > account.getBalance()) {
            throw new NotEnoughFundsException();
        } else {
            float b = account.getBalance() - x;
            account.setBalance(b);
        }
    }
        else if (account instanceof CheckingAccount) {
            if (x > account.getBalance()) {

                ((CheckingAccount) account).setOverdraft(((CheckingAccount) account).getOverdraft()- (x - account.getBalance()));
                if(((CheckingAccount) account).getOverdraft() < 0 ){
                    throw new OverDraftLimitExceededException(x);
                }

            } else if ((account.getBalance() + ((CheckingAccount) account).getOverdraft()) >= x) {
                float newBalance = account.getBalance()  - x;
                account.setBalance(newBalance);
            }

        }
    }

    @Override
    public float decimalValue(Account account) {
        float res = (float) (Math.rint(100.0 * account.getBalance()) / 100.0);

        return res;
    }

    @Override
    public Account getAccountById (Integer id) {

AccountDAO accountDAO = DAOFactory.getAccountDAO();
        Account account = null;
        try {
            account = accountDAO.getAccountById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  account;
    }

    @Override
    public void Transfer(Integer accIdWithdraw, Integer accIdDeposit, Integer clIdWithdraw, Integer clIdDeposit, float amount) {


       AccountDAO accountDAO = DAOFactory.getAccountDAO();
        try {
            accountDAO.transfer(accIdWithdraw, accIdDeposit , clIdWithdraw , clIdDeposit, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
