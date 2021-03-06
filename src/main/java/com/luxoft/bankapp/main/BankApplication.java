package com.luxoft.bankapp.main;

import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;

import java.sql.SQLException;
import java.util.Scanner;

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


        BankService bankService = ServiceFactory.getBankImpl();



        try {
            currentBank = bankService.getBankByName(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(currentBank.toString());
        bankService.getBankInfo(currentBank);



    }

}