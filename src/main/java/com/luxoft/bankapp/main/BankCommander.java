package com.luxoft.bankapp.main;

import com.luxoft.bankapp.command.*;
import com.luxoft.bankapp.dao.*;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by acer on 15.01.2015.
 */
public class BankCommander {

    private Map <String, Command> commandsMap;

    public static void main(String args[]) {


        ApplicationContext context =
                new ClassPathXmlApplicationContext("application-context..xml");

        BankCommander bankCommander = (BankCommander) context.getBean("bankCommander");
        BankService bankService = (BankService) context.getBean("bankService");
        ClientService clientService = (ClientService) context.getBean("clientService");
        Map<String, AbstractCommand> commandMap = bankCommander.getCommandsMap();

        Bank currentBank = null;
        try {
            currentBank = bankService.getBankByName("My Bank");
        } catch (SQLException e) {
           System.out.println("Bank was not found");
        }
        System.out.println("Current bank: " + currentBank.toString());


        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        System.out.println("Input current client name: ");
        sb.append(scanner.nextLine().trim());


    String clientName = sb.toString();
        try {
      Client currentClient = clientService.findClientInDB(currentBank, clientName);
            if(currentClient == null) {
                System.out.println("There is no client with such name");
            }

        System.out.println("Current client: " + currentClient.toString());

            for (AbstractCommand command : commandMap.values()) {
                command.setCurrentClient(currentClient);
                command.setCurrentBank(currentBank);
            }

        Iterator iterator = commandMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Command> printMap = (Map.Entry<String, Command>) iterator.next();
            printMap.getValue().printCommandInfo();
            System.out.print(" press => ");
            System.out.print("'"+ printMap.getKey()+"'");
            System.out.println("");
        }
        String command;

        while (true) {

            System.out.println("");

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
                    System.out.print("......");
                    printMapIt.getValue().execute(); //start working command
                }

            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setCommandsMap(Map commandsMap) {
        this.commandsMap = commandsMap;
    }

    public Map getCommandsMap() {
        return commandsMap;
    }
}
