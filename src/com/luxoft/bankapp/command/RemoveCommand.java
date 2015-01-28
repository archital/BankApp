package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;

/**
 * Created by acer on 24.01.2015.
 */
public class RemoveCommand implements Command {



	private Bank currentBank;



	public RemoveCommand (Bank currentBank) {

		this.currentBank = currentBank;
	}

	@Override
	public void execute () throws ClientExistsException {

		if (currentBank == null) {
			inOut.println("Error!!! Current bank is undefined");
			return;
		}
		inOut.println("Input client name: ");

		String name = inOut.readln();

		Client currentClient = null;
		BankImpl bankImp = new BankImpl();


		try {
			currentClient = bankImp.getClient(currentBank, name);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}


		if (currentClient == null) {
			inOut.println("Error!!! Client with that name already remove ");
			return;
		}

		bankImp.removeClient(currentBank, currentClient);
		inOut.println("Client remove successful ! \n enter 'back'/ 'exit' or 'bye'");

	}

	@Override
	public void printCommandInfo () {
		inOut.println("Remove command");
	}
}
