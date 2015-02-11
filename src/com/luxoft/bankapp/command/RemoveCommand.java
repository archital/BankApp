package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.BankServer;
import com.luxoft.bankapp.server.Current;
import com.luxoft.bankapp.service.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 24.01.2015.
 */
public class RemoveCommand implements Command {


	private InputOutput inOut;
	private Bank currentBank;
    private  Account currentAccount;
    private Integer accId;
    private Current current;



	public RemoveCommand(InputOutput inputOutput, Bank currentBank) {

		this.inOut = inputOutput;
		this.currentBank = currentBank;
	}

    public RemoveCommand (InputOutput inOut) {

        this.inOut = inOut;
    }

	@Override
	public synchronized void execute () {

        BankCommander.currentBank = currentBank;
           current.setCurrentBank(currentBank);

		if (currentBank == null) {
			inOut.println("Error!!! Current bank is undefined");
			return;
		}
		inOut.println("Input client name: ");

		String name = inOut.readln();

		Client currentClient = null;
        ClientService clientService = ServiceFactory.getClientImpl();


        try {
            currentClient = clientService.findClientInDB(currentBank, name);
        } catch (ClientNotFoundException e) {
            inOut.println("Client with that name was not found");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (currentClient == null) {
			inOut.println("Error!!! Client with that name already remove ");
			return;
		}

        inOut.println("Enter client name, to select new current client");

        String newCurrentName = inOut.readln();

        Client newCurrentClient = null;
        try {
            newCurrentClient = clientService.findClientInDB(currentBank, newCurrentName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            inOut.println("Client with that name was not found");
        }

        if (newCurrentClient == null) {
            inOut.println("Error!!! Client with such name was not found.");
            return;
        }
        AccountService accountService = ServiceFactory.getAccountImpl();

        try {
            if (accountService.getClientAccounts(newCurrentClient.getId()).isEmpty()) {
                inOut.println("Client: " + newCurrentClient.getGender().getGenderPrefix() + newCurrentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            } else {

                List<Account> accountList = accountService.getClientAccounts(newCurrentClient.getId());

                inOut.println("Enter account ID to make this account 'current': \n" +
                        accountList.toString());


                accId = Integer.parseInt(inOut.readln());
                currentAccount = accountService.getAccountById(accId);


                newCurrentClient.setActiveAccount(currentAccount);

                BankCommander.currentClient = newCurrentClient; // set currentClient to BankCommander
                BankServer.currentClient = newCurrentClient;
                current.setCurrentClient(newCurrentClient);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        inOut.println("Client remove successful !\n" +
                "Your new current client is : "+
                newCurrentName.toString()+
                " You can select new command " +
                "\npress 'Enter'  ");
        try {
            clientService.removeClient(currentClient, currentBank);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

	@Override
	public void printCommandInfo () {
		inOut.println("Remove command");
	}
}
