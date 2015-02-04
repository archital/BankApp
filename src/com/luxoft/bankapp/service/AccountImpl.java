package com.luxoft.bankapp.service;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
<<<<<<< HEAD
import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.DAOException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;
=======
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.exception.OverDraftLimitExceededException;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
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

<<<<<<< HEAD
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

        try {
            accountDAO.save(account, client);
        } catch (DAOException e) {
            e.printStackTrace();
        }
=======
    @Override
    public void addAccount(Client client, Account account) throws ClientExistsException, SQLException {
        AccountDAO accountDAO = new AccountDAOImpl();

        accountDAO.save(account, client);
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

    }

    @Override
    public List<Account> getClientAccounts(Integer id) throws SQLException {
<<<<<<< HEAD
        AccountDAO accountDAO = DAOFactory.getAccountDAO();
=======
        AccountDAO accountDAO = new AccountDAOImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

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
<<<<<<< HEAD
    public void withdraw(float x , Account account) throws NotEnoughFundsException,  OverDraftLimitExceededException{
=======
    public void withdraw(float x , Account account) throws NotEnoughFundsException {
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

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

<<<<<<< HEAD
AccountDAO accountDAO = DAOFactory.getAccountDAO();
=======
AccountDAO accountDAO = new AccountDAOImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
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


<<<<<<< HEAD
       AccountDAO accountDAO = DAOFactory.getAccountDAO();
=======
       AccountDAO accountDAO = new AccountDAOImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
        try {
            accountDAO.transfer(accIdWithdraw, accIdDeposit , clIdWithdraw , clIdDeposit, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
