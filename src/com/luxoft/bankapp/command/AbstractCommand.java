package com.luxoft.bankapp.command;

<<<<<<< HEAD
import com.luxoft.bankapp.dao.DAOFactory;
import com.luxoft.bankapp.expeption.ClientExistsException;
=======
import com.luxoft.bankapp.exception.ClientExistsException;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
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





    public AbstractCommand(InputOutput io) {
        this.ioStreams = io;
    }


	public AbstractCommand() {
        ioStreams = new InputOutput();
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
