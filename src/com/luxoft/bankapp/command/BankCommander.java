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


        }

    }



    public void removeCommand(String name) {

        Iterator iterator = commandMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> printMap = (Map.Entry<String, Command>) iterator.next();

            if (printMap.getKey().equals(name)) {

                commandMap.remove(name);
            }


        }

    }


    static Map<String, Command> commandMap = new HashMap<String, Command>();


    public static void main(String args[]) {
        InputOutput io = new InputOutput();

        BankDAO bankDAO = new BankDAOImpl();
        try {
            currentBank = bankDAO.getBankByName(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        System.out.println(currentBank.toString());




        commandMap.put("0", new FindClientCommand(io, currentBank));
        commandMap.put("1", new GetAccountsCommand(io, currentBank, currentClient));
        commandMap.put("2", new WithdrawCommand(io, currentBank, currentClient));
        commandMap.put("3", new DepositCommand(io, currentBank, currentClient));
        commandMap.put("4", new TransferCommand(io, currentBank, currentClient));
        commandMap.put("5", new AddClientCommand(io, currentBank));
        commandMap.put("6", new ReportCommander(io, currentBank));
        commandMap.put("7", new RemoveCommand(io, currentBank));
        commandMap.put("8", new Command() { // 6 - Exit Command
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
            System.out.print(printMap.getKey());
            System.out.println();


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
