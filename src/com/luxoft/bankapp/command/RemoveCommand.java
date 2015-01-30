package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.main.BankCommander;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.CommanderServer;
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



	public RemoveCommand(InputOutput inputOutput, Bank currentBank) {

		this.inOut = inputOutput;
		this.currentBank = currentBank;
	}

    public RemoveCommand (InputOutput inOut) {

        this.inOut = inOut;
    }

	@Override
	public void execute () throws ClientExistsException {

        BankCommander.currentBank = currentBank;

		if (currentBank == null) {
			inOut.println("Error!!! Current bank is undefined");
			return;
		}
		inOut.println("Input client name: ");

		String name = inOut.readln();

		Client currentClient = null;
        ClientService clientService = new ClientImpl();


        try {
            currentClient = clientService.findClientInDB(currentBank, name);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        if (newCurrentClient == null) {
            inOut.println("Error!!! Client with such name was not found.");
            return;
        }
        AccountService accountService = new AccountImpl();

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
                CommanderServer.currentClient = newCurrentClient;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        inOut.println("Client remove successful !\n" +
                "Your new current client is : "+
                newCurrentName.toString()+
                " You can select new command " +
                "\npress 'Enter' for CommanderServer ");
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
