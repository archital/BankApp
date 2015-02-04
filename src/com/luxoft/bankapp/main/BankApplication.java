package com.luxoft.bankapp.main;

<<<<<<< HEAD
import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
=======
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;

import java.sql.SQLException;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
=======
import java.util.Scanner;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

/**
 * Created by SCJP on 14.01.2015.
 */
public class BankApplication {

    /**
     * @param args the command line arguments
     */
    private Bank  currentBank;



    public static void main(String[] args) {


        BankApplication  bankApplication = new BankApplication();

        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        System.out.println("Input current bank name: ");
         sb.append(scanner.nextLine().trim());

        String bankName = sb.toString();
        try {
            bankApplication.printBankReport(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }




    public void printBankReport(String bankName) throws SQLException {


<<<<<<< HEAD
        BankService bankService = ServiceFactory.getBankImpl();
=======
        BankService bankService = new BankImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3



        try {
            currentBank = bankService.getBankByName(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(currentBank.toString());
        bankService.getBankInfo(currentBank);



    }

}