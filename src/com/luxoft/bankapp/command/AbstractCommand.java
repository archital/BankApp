package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;

/**
 * Created by acer on 24.01.2015.
 */
public class AbstractCommand implements Command{

	protected Client currentClient;
	protected Bank currentBank;
	protected BankService bankService = new BankImpl();
	protected InputOutput ioStreams;



	public AbstractCommand() {
	}

	protected AbstractCommand(InputOutput ioStreams) {
		this.ioStreams = ioStreams;
	}

	protected AbstractCommand(Client currentClient, Bank currentBank, InputOutput ioStreams) {
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
