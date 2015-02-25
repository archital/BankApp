package com.luxoft.bankapp.command;

import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.Current;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.ServiceFactory;

/**
 * Created by acer on 24.01.2015.
 */
public class AbstractCommand implements Command {

	protected Client currentClient;
	protected Bank currentBank;
	protected BankService bankService = ServiceFactory.getBankImpl();
	protected InputOutput ioStreams;


    public AbstractCommand() {
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
	public void execute ()  {

	}


    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    public Bank getCurrentBank() {
        return currentBank;
    }

    public void setCurrentBank(Bank currentBank) {
        this.currentBank = currentBank;
    }

    @Override
	public void printCommandInfo () {

	}
}
