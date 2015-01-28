package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.*;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by acer on 15.01.2015.
 */
public class BankCommander {
    public static Bank currentBank;
    public static Client currentClient = null;
    public static BankService service = new BankImpl();
    static String bankName = "My Bank";




    public void registerCommand(String name, Command command) {

        Iterator iterator = commandMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> printMap = (Map.Entry<String, Command>) iterator.next();

            if (!(printMap.getValue().equals(command)) && !(printMap.getKey().equals(name))) {

                commandMap.put(name, command);
            }
            ;

        }

    }

    ;

    public void removeCommand(String name) {

        Iterator iterator = commandMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> printMap = (Map.Entry<String, Command>) iterator.next();

            if (printMap.getKey().equals(name)) {

                commandMap.remove(name);
            }
            ;

        }

    }


    static Map<String, Command> commandMap = new HashMap<String, Command>();


    public static void main(String args[]) {

        BankDAO bankDAO = new BankDAOImpl();
        try {
            currentBank = bankDAO.getBankByName(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        System.out.println(currentBank.toString());
//
//        ClientDAO clientDAO = new ClientDAOImpl();
//
//        System.out.println("Find client by name");
//        try {
//          currentClient =  clientDAO.findClientByName(currentBank, "Nana");
//        } catch (ClientNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//        System.out.println("current client " + currentClient.toString());
//        System.out.println("Find client by id");
//
//        try {
//            currentClient = clientDAO.findClientById(3);
//        } catch (ClientNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClientExistsException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("current client " +currentClient.toString());
//
//
//
//
//        try {
//
//            System.out.println("save Client");
//            clientDAO.save(currentClient, currentBank.getId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//
//        System.out.println("get All Clients ");
//
//        try {
//            List<Client> clientList = clientDAO.getAllClients(currentBank);
//
//            for (Client c : clientList) {
//
//                System.out.println(c.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//

//        System.out.println("remove Client");
//        try {
//            clientDAO.remove(currentClient);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//
//        System.out.println("get Client Accounts");
//
//        AccountDAO accountDAO = new AccountDAOImpl();
//        try {
//            List<Account> accounts =    accountDAO.getClientAccounts(currentClient.getId());
//
//            for (Account a : accounts) {
//
//                currentClient.setActiveAccount(a);
//                System.out.println(a.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//
//        System.out.println("save Account");
//
//        try {
//            accountDAO.save(currentClient.getActiveAccount(), currentClient);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }



        commandMap.put("0", new FindClientCommand(currentBank));
        commandMap.put("1", new GetAccountsCommand(currentBank));
        commandMap.put("2", new WithdrawCommand(currentBank));
        commandMap.put("3", new DepositCommand(currentBank));
        commandMap.put("4", new TransferCommand(currentBank));
        commandMap.put("5", new AddClientCommand(currentBank));
        commandMap.put("6", new Command() { // 6 - Exit Command
            public void execute() {
                System.exit(0);
            }

            public void printCommandInfo() {
                System.out.print("Exit");
            }
        });


        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();


        Iterator iterator = commandMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> printMap = (Map.Entry<String, Command>) iterator.next();
            System.out.println();
            printMap.getValue().printCommandInfo();
            System.out.print("  press => ");
            System.out.println(printMap.getKey());


        }

        String command;

        while (true) {
            while (sb.length() == 0) {
                System.out.println();
                System.out.println("Input command number: ");
                sb.append(scanner.nextLine().trim());
            }
            try {
                command = sb.toString();
            } catch (RuntimeException e) {
                System.out.println("Error! Illegal number value.\n");
                return;
            }
            sb.delete(0, sb.length());
            System.out.print("Executing command ");

            Iterator iter = commandMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Command> printMapIt = (Map.Entry<String, Command>) iter.next();
                if (printMapIt.getKey().equals(command)) {

                    printMapIt.getValue().printCommandInfo();
                    System.out.print("...\n\n");
                    try {
                        printMapIt.getValue().execute(); //start working command
                    } catch (ClientExistsException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
