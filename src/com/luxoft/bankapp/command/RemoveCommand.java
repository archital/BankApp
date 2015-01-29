package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;

import java.sql.SQLException;

/**
 * Created by acer on 24.01.2015.
 */
public class RemoveCommand implements Command {


	private InputOutput inOut;
	private Bank currentBank;



	public RemoveCommand(InputOutput inputOutput, Bank currentBank) {

		this.inOut = inputOutput;
		this.currentBank = currentBank;
	}

    public RemoveCommand (InputOutput inOut) {

        this.inOut = inOut;
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
        ClientDAO clientDAO = new ClientDAOImpl();


        try {
            currentClient = clientDAO.findClientByName(currentBank, name);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (currentClient == null) {
			inOut.println("Error!!! Client with that name already remove ");
			return;
		}

        try {
            clientDAO.remove(currentClient);

            currentClient = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        inOut.println("Client remove successful ! \n enter 'back'/ 'exit' or 'bye'");
        inOut.println("Client remove successful !");
        try {
            clientDAO.remove(currentClient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

	@Override
	public void printCommandInfo () {
		inOut.println("Remove command");
	}
}
