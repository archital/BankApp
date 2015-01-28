package com.luxoft.bankapp.server.command;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.command.InputOutput;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;

/**
 * Created by acer on 24.01.2015.
 */
public class ServerAbstractCommand implements Command {

	protected Client currentClient;
	protected Bank currentBank;
	protected BankService bankService = new BankImpl();
	protected InputOutput ioStreams;



	public ServerAbstractCommand () {
	}

	protected ServerAbstractCommand (InputOutput ioStreams) {
		this.ioStreams = ioStreams;
	}

	protected ServerAbstractCommand (Client currentClient, Bank currentBank, InputOutput ioStreams) {
		this.currentClient = currentClient;
		this.currentBank = currentBank;
		this.ioStreams = ioStreams;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public InputOutput getIoStreams() {
		return ioStreams;
	}

	public void setIoStreams(InputOutput ioStreams) {
		this.ioStreams = ioStreams;
	}


	@Override
	public void execute () throws ClientExistsException {

	}

	@Override
	public void printCommandInfo () {

	}
}
