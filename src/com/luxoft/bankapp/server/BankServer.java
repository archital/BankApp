package com.luxoft.bankapp.server;

/**
 * Created by acer on 24.01.2015.
 */


import com.luxoft.bankapp.command.*;
import com.luxoft.bankapp.exception.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;


public class BankServer {


	public static Bank currentBank = null;
	public static Client currentClient = null;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	ServerSocket serverSocket;
	Socket connection = null;
	String message = null;
    static String bankName = "My Bank";
    static  String clientName = "";





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
                serverSocket = new ServerSocket(2004, 10);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 2. Wait for connection
			System.out.println("Waiting for connection");
			try {
				connection = serverSocket.accept();
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

			final InputOutput inputOutput = new InputOutput(in, out);


			commandMap.put("0", new FindClientCommand(inputOutput, currentBank));
			commandMap.put("1", new GetAccountsCommand(inputOutput, currentBank, currentClient));
			commandMap.put("2", new WithdrawCommand(inputOutput, currentBank, currentClient));
			commandMap.put("3", new DepositCommand(inputOutput, currentBank, currentClient));
			commandMap.put("4", new TransferCommand(inputOutput, currentBank, currentClient));
			commandMap.put("5", new AddClientCommand(inputOutput,currentBank));
			commandMap.put("6", new RemoveCommand(inputOutput, currentBank));
			commandMap.put("7", new Command() { // 7 - Exit Command
				public void execute() {
					sendMessage("bye");
					System.exit(0);
				}

				public void printCommandInfo() {
					inputOutput.println("Exit");
				}
			});


            ClientService clientService = ServiceFactory.getClientImpl();

            sendMessage("Input current client name: ");
            clientName =  (String) in.readObject();


            try {
                currentClient = clientService.findClientInDB(currentBank, clientName);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }


            if(currentClient == null){

                sendMessage("bye");
                message = "bye";
            }



            do {

	            sendMessage("You select "+
			            currentClient.toString()+ "Chose command you need: \n" + " to find client press => '0'\n " +
			            " to get Accounts and balances press => '1'\n " +
			            " to make Withdraw press => '2'\n " +
			            " to make Deposit press => '3'\n " +
			            " to make Transfer press => '4'\n " +
			            " to add Client press => '5'\n " +
			            " to remove Client press => '6'\n " +
			            "to exit  press => '7' or 'bye'");

	            message = (String) in.readObject();
                if (message.equals("0")) {
                    new FindClientCommand(inputOutput, currentBank).execute();
                    message = (String) in.readObject();

                } else if (message.equals("5")) {
                    new AddClientCommand(inputOutput, currentBank).execute();
                    message = (String) in.readObject();

                } else if (message.equals("3")) {
                    new DepositCommand(inputOutput, currentBank, currentClient).execute();
                    message = (String) in.readObject();

                } else if (message.equals("2")) {
                    new WithdrawCommand(inputOutput, currentBank, currentClient).execute();
                    message = (String) in.readObject();

                } else if (message.equals("4")) {
                    new TransferCommand(inputOutput, currentBank, currentClient).execute();
                    message = (String) in.readObject();

                } else if (message.equals("1")) {
                    new GetAccountsCommand(inputOutput, currentBank, currentClient).execute();
                    message = (String) in.readObject();

                } else if (message.equals("6")) {
                    new RemoveCommand(inputOutput, currentBank).execute();
                    message = (String) in.readObject();

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
			e.printStackTrace();
		}  catch (IOException e) {
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

        BankService bankService = ServiceFactory.getBankImpl();


        try {
            currentBank = bankService.getBankByName(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }


		BankServer server = new BankServer();
		while (true) {

			server.run();
		}
	}
}

