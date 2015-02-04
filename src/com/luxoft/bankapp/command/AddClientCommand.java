




package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.server.CommanderServer;
import com.luxoft.bankapp.service.ClientImpl;
import com.luxoft.bankapp.service.ClientService;
import com.luxoft.bankapp.service.ServiceFactory;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by acer on 15.01.2015.
 */
public class AddClientCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
     private Gender gender;

    public AddClientCommand(InputOutput inOut, Bank currentBank) {
        this.inOut = inOut;
        this.currentBank = currentBank;

    }

    public AddClientCommand() {
    }

    @Override
    public void execute()  {



        ClientService clientService = ServiceFactory.getClientImpl();
        if (currentBank == null) {
            inOut.println("Error!!! Current bank is undefined");
            return;
        }

        inOut.println("Input client name: ");

        String name = inOut.readln();

        Client currentClient = null;


        try {
            currentClient = clientService.findClientInDB(currentBank, name);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (!(currentClient == null)) {
            inOut.println("Error!!! Client with that name already added ");
            return;
        }

        inOut.println("Input client gender ( M/F ): ");

         String gend = inOut.readln();

            Pattern pattern = Pattern.compile("[MmFf]");
        Matcher matcher = pattern.matcher(gend);

            if (gend.equals("m")||gend.equals("M")) {
            gender = Gender.MALE;
                inOut.println("Input client overdraft: ");
    } else if (gend.equals("f")||gend.equals("F")) {
            gender = Gender.FEMALE;
                inOut.println("Input client overdraft: ");
  } else if (!matcher.matches()){
                inOut.println("Error!!! Illegal gender description");
                return;
            }



        float overdraft;
        try {
            overdraft = Float.parseFloat(inOut.readln());
        } catch (RuntimeException e) {
            inOut.println("Error!!! Illegal number value. ");
            return;
        }
        if (overdraft < 0) {
            inOut.println("Error!!! Value must be positive. ");
            return;
        }

            inOut.println("Input client telephone number: ");

        pattern = Pattern.compile("^\\(?|^\\+?(\\d{3}|\\d{5})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");

       String telephone = inOut.readln().trim();
        matcher = pattern.matcher(telephone);
        if (!matcher.matches()) {
            inOut.println("Error!!! Illegal telephone number.");
            return;
        }


            inOut.println("Input client e-mail: ");


        pattern = Pattern.compile("([a-zA-Z][\\w]*)@([a-zA-Z][\\w]*[.])*((net)|(com)|(org)|(ru) )");

        String email = inOut.readln().trim();
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            inOut.println("Error!!! Illegal e-mail address.");
            return;
        }

        inOut.println("Enter Client City ");
        String city = inOut.readln();

        Client client = new Client();

        client.setName(name);
        client.setInitialOverdraft(overdraft);
        client.setTelephoneNumber(telephone);
        client.setGender(gender);
        client.setEmail(email);
        client.setCity(city);


        try {
            try {
                clientService.addClient(currentBank, client);
            } catch (ClientExistsException e) {
                inOut.println("Client with such name already exists");
            }

            BankCommander.currentClient  = client;
            CommanderServer.currentClient = client;

            inOut.println("Client is added and selected as 'current client': " + client.toString()+"\n you can select new command\n" +
                    "press 'Enter' for CommanderServer ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        inOut.println("Add Client");
    }

}

