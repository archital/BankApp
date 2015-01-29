package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.*;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankInfo;

import java.sql.SQLException;

/**
 * Created by ${ArthurPopichenko} on 28.01.2015.
 */
public class ReportCommander implements Command {

	private InputOutput ioStreams;
	private Bank currentBank;



	public ReportCommander(InputOutput inputOutput, Bank currentBank) {
		this.ioStreams = inputOutput;
		this.currentBank = currentBank;

	}

	public ReportCommander () {
	}

	@Override
	public void execute () throws ClientExistsException {

		if (currentBank == null) {
			ioStreams.println("Error!!! Current bank is undefined.");
			return;
		}

	        BankDAO bankDAO = new BankDAOImpl();
		BankInfo bankInfo = null;
		try {
			bankInfo = bankDAO.getBankInfo(currentBank);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
			sb.append("Bank found: " + '\n');
			sb.append("Number of clients: " + bankInfo.getNumberOfClients() + '\n');
			sb.append("Total accounts sum: " + bankInfo.getTotalAccountSum() + '\n');
			sb.append("Clients GROUP BY city: " + bankInfo.getClientsByCity() + '\n');
		    sb.append("Clients sorted by balance: " + bankInfo.getClientsSorted() + '\n');

			ioStreams.println(sb.toString());
		ioStreams.println("Enter new command ");
		}


	@Override
	public void printCommandInfo () {
		ioStreams.println("Report command");
	}
}
