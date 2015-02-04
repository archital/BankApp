package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 15.01.2015.
 */

<<<<<<< HEAD
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.expeption.DAOException;
import com.luxoft.bankapp.model.Account;
=======
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
import com.luxoft.bankapp.model.Bank;

<<<<<<< HEAD
import java.io.FileNotFoundException;
import java.io.IOException;
=======
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
import java.sql.SQLException;

public interface BankService {

    Bank getBankByName(String name) throws SQLException;
    BankInfo getBankInfo(Bank bank) throws SQLException;
<<<<<<< HEAD
    public void  save(Bank bank) throws SQLException, ClientExistsException, DAOException;
=======
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
}
