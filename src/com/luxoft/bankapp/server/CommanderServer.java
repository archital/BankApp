package com.luxoft.bankapp.server;

/**
 * Created by acer on 24.01.2015.
 */


import com.luxoft.bankapp.command.*;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.service.BankService;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class CommanderServer extends AbstractServer{


	public static Bank currentBank = null;
	public static Client currentClient = null;
	public static BankService service = new BankImpl();
	private ObjectOutputStream out;
	private ObjectInputStream in;
	ServerSocket serverSocket;
	Socket connection = null;
	String message = null;





	static Map<String, Command> commandMap = new HashMap<String, Command>();


	void sendMessage (final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}


	public void run() {

		try {
			// 1. creating a server socket
			try {
				providerSocket = new ServerSocket(2004, 10);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 2. Wait for connection
			System.out.println("Waiting for connection");
			try {
				connection = providerSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Connection received from "
					+ connection.getInetAddress().getHostName());
			// 3. get Input and Output streams
			try {
				out = new ObjectOutputStream(connection.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in = new ObjectInputStream(connection.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			InputOutput inputOutput = new InputOutput(in, out);


			commandMap.put("find", new FindClientCommand(inputOutput, currentBank));
			commandMap.put("balance", new GetAccountsCommand(inputOutput, currentBank, currentClient));
			commandMap.put("withdraw", new WithdrawCommand(inputOutput, currentBank, currentClient));
			commandMap.put("deposit", new DepositCommand(inputOutput, currentBank, currentClient));
			commandMap.put("transfer", new TransferCommand(inputOutput, currentBank, currentClient));
			commandMap.put("add", new AddClientCommand(inputOutput,currentBank));
			commandMap.put("remove", new RemoveCommand(inputOutput, currentBank));



			Iterator iterator = commandMap.entrySet().iterator();


			do {

					sendMessage("Enter the name of the client with whom you want to work\n");
					String	name= (String) in.readObject();

				currentClient = service.findClient(currentBank, name);

				if(currentClient == null){

						sendMessage("bye");
						message = "bye";

				}

					sendMessage("Yo select "+
							currentClient.toString()+ "Chose command you need: \n" + "'find'\n " +
						"'deposit'\n" +"'balance'\n" + "'add'\n" + "'remove'\n" + "'transfer'\n" +
						"'bye'\n");

				message = (String) in.readObject();

							if (message.equals("find")) {
					new FindClientCommand(inputOutput, currentBank).execute();
					message = (String) in.readObject();

				} else if (message.equals("add")) {
					new AddClientCommand(inputOutput, currentBank).execute();
					message = (String) in.readObject();

				} else if (message.equals("deposit")) {
					new DepositCommand(inputOutput, currentBank, currentClient).execute();
					message = (String) in.readObject();

				} else if (message.equals("withdraw")) {
					new WithdrawCommand(inputOutput, currentBank, currentClient).execute();
					message = (String) in.readObject();

				} else if (message.equals("transfer")) {
					new TransferCommand(inputOutput, currentBank, currentClient).execute();
					message = (String) in.readObject();

				} else if (message.equals("balance")) {
					new GetAccountsCommand(inputOutput, currentBank, currentClient).execute();
					message = (String) in.readObject();

				} else if (message.equals("remove")) {
								new RemoveCommand(inputOutput, currentBank).execute();
								message = (String) in.readObject();

							} else if (message.equals("bye")) {
					sendMessage("bye");

				} else {

					sendMessage("bye");

				}
        } while (!message.equals("bye"));


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClientExistsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}




	public static void main (String args[]) {

		AbstractServer abstractServer = new AbstractServer();
		abstractServer.initialize();           //initialized server with data
		currentBank = abstractServer.getCurrentBank();
		CommanderServer server = new CommanderServer();
		while (true) {

			server.run();
		}
	}
}

