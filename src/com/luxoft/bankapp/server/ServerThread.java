package com.luxoft.bankapp.server;

import com.luxoft.bankapp.command.*;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.ClientService;
import com.luxoft.bankapp.service.ServiceFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by SCJP on 05.02.2015.
 */
public class ServerThread  implements Runnable{


	private ObjectOutputStream out;
	private ObjectInputStream in;
    private Socket connection = null;
	String message = null;
	static String bankName = "My Bank";
	static  String clientName = "";
	static Map<String, Command> commandMap = new HashMap<String, Command>();
    private static final  Logger logger = Logger.getLogger(ServerThread.class.getName());

	public ServerThread (Socket accept) {

		this.connection = accept;
	}
	
	@Override
	public void run () {
		logger.setLevel(Level.FINEST);

		BankServerThreaded.atomicInteger.decrementAndGet();
		BankService bankService = ServiceFactory.getBankImpl();
        Current current = new CurrentImpl();


		try {
synchronized (current) {
	Bank currentBank = bankService.getBankByName(bankName);

			current.setCurrentBank(currentBank);
}
		} catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage() + "SQL Exception  ", e);
		}

		try {

			System.out.println("Connection received from "
					+ connection.getInetAddress().getHostName());

            logger.log(Level.INFO, "Connection received from " + connection.getInetAddress().getHostName());
			// 3. get Input and Output streams
			try {
				out = new ObjectOutputStream(connection.getOutputStream());
			} catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage() + "I/O Exception  ", e);
			}
			try {
				out.flush();
			} catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage() + "cant flush output  ", e);
			}
			try {
				in = new ObjectInputStream(connection.getInputStream());
			} catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage() + "I/O Exception  ", e);
			}

			final InputOutput inputOutput = new InputOutput(in, out);


			commandMap.put("0", new FindClientCommand(inputOutput, current.getCurrentBank()));
			commandMap.put("1", new GetAccountsCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()));
			commandMap.put("2", new WithdrawCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()));
			commandMap.put("3", new DepositCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()));
			commandMap.put("4", new TransferCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()));
			commandMap.put("5", new AddClientCommand(inputOutput,current.getCurrentBank()));
			commandMap.put("6", new RemoveCommand(inputOutput, current.getCurrentBank()));
			commandMap.put("7", new Command() { // 7 - Exit Command
				public void execute() {
					sendMessage("bye");
                    logger.log(Level.FINEST, "Send message 'bye' ");
					System.exit(0);
				}

				public void printCommandInfo() {
					inputOutput.println("Exit");
				}
			});


			ClientService clientService = ServiceFactory.getClientImpl();

			sendMessage("Input current client name: ");


				clientName =  (String) in.readObject();
            logger.log(Level.FINEST, "client name: "+ clientName);



            try {
			Client	currentClient = clientService.findClientInDB(current.getCurrentBank(), clientName);
				if(currentClient == null){

					sendMessage("bye");
					message = "bye";
				}

				current.setCurrentClient(currentClient);
			} catch (SQLException e) {
	            logger.log(Level.SEVERE, e.getMessage() + "sql Exception  ", e);
			} catch (ClientNotFoundException e) {
	            logger.log(Level.SEVERE, e.getMessage() + "Client  was not found in DB" , e);
			}

			do {

				sendMessage("You select "+
						current.getCurrentClient().toString()+ "Chose command you need: \n" + " to find client press => '0'\n " +
						" to get Accounts and balances press => '1'\n " +
						" to make Withdraw press => '2'\n " +
						" to make Deposit press => '3'\n " +
						" to make Transfer press => '4'\n " +
						" to add Client press => '5'\n " +
						" to remove Client press => '6'\n " +
						"to exit  press => '7' or 'bye'");

				message = (String) in.readObject();
				if (message.equals("0")) {
					new FindClientCommand(inputOutput, current.getCurrentBank()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("5")) {
					new AddClientCommand(inputOutput, current.getCurrentBank()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("3")) {
					new DepositCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("2")) {
					new WithdrawCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("4")) {
					new TransferCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("1")) {
					new GetAccountsCommand(inputOutput, current.getCurrentBank(), current.getCurrentClient()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("6")) {
					new RemoveCommand(inputOutput, current.getCurrentBank()).execute();
					message = (String) in.readObject();
                    logger.log(Level.FINEST, message);

				} else if (message.equals("7")) {
					sendMessage("bye");
					message = "bye";

				} else {
					sendMessage("Error! wrong command: ");
					message = (String) in.readObject();

				}

				if (message.equals("bye")) {
					sendMessage("bye"); }

			} while (!message.equals("bye"));


		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}  catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage() + "I/O Exception  ", e);
		} finally {
			try {
				in.close();
				out.close();
			connection.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage() + "I/O Exception  ", e);
			}
		}
	}

	public synchronized void sendMessage (final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			logger.log(Level.FINEST, "server>" + msg);
		} catch (IOException ioException) {
			logger.log(Level.SEVERE, ioException.getMessage() + "I/O Exception  ", ioException);
		}
	}

}
