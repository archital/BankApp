package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by acer on 15.01.2015.
 */
public class AddClientCommand implements Command {

    @Override
    public void execute() throws ClientExistsException {
        if (BankCommander.currentBank == null) {
            System.out.println("Error!!! Current bank is undefined");
         return;
        }
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        while (sb.length() == 0) {
            System.out.println("Input client name: ");
            sb.delete(0, sb.length());
            sb.append(scanner.nextLine().trim());
        }
        String clientName = sb.toString();
        sb.delete(0, sb.length());

        while (sb.length() == 0) {
            System.out.println("Input client gender ( M/F ): ");
            sb.delete(0, sb.length());
            sb.append(scanner.nextLine().trim());
        }
        Pattern pattern = Pattern.compile("[MmFf]");
        Matcher matcher = pattern.matcher(sb);
        if (!matcher.matches()) {
            System.out.println("Error!!! Illegal gender description");
            return;
        }
        Gender gender;
        if (sb.toString().equals("m") || sb.toString().equals("M"))
            gender = Gender.MALE;
        else {
            gender = Gender.FEMALE;
        }
        sb.delete(0, sb.length());

        while (sb.length() == 0) {
            System.out.println("Input client overdraft: ");
            sb.delete(0, sb.length());
            sb.append(scanner.nextLine().trim());
        }
        float overdraft;
        try {
            overdraft = Float.parseFloat(sb.toString());
        } catch (RuntimeException e) {
            System.out.println("Error!!! Illegal number value. ");
            return;
        }
        if (overdraft < 0) {
            System.out.println("Error!!! Value must be positive. ");
            return;
        }
        sb.delete(0, sb.length());

        while (sb.length() == 0) {
            System.out.println("Input client telephone number: ");
            sb.delete(0, sb.length());
            sb.append(scanner.nextLine().trim());
       }
        pattern = Pattern.compile("^\\(?|^\\+?(\\d{3}|\\d{5})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");
        matcher = pattern.matcher(sb);
        if (!matcher.matches()) {
            System.out.println("Error!!! Illegal telephone number.");
            return;
        }
        String telString = sb.toString();
        sb.delete(0, sb.length());

        while (sb.length() == 0) {
            System.out.println("Input client e-mail: ");
            sb.delete(0, sb.length());
            sb.append(scanner.nextLine().trim());
        }
        pattern = Pattern.compile("([a-zA-Z][\\w]*)@([a-zA-Z][\\w]*[.])*((net)|(com)|(org))");
        matcher = pattern.matcher(sb);
        if (!matcher.matches()) {
            System.out.println("Error!!! Illegal e-mail address.");
           return;
        }

        Client client = new Client();
        client.setEmail(sb.toString());
        client.setName(clientName);
        client.setInitialOverdraft(overdraft);
        client.setTelephoneNumber(telString);
        client.setGender(gender);

        System.out.println("Client is added: " + client.toString());
        BankCommander.service.addClient(BankCommander.currentBank, client);
        BankCommander.currentClient = client;
        System.out.println("Client is selected: ");
        System.out.println(client.toString());
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Add Client");
    }
}

