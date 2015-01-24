package com.luxoft.bankapp.server;

import com.luxoft.bankapp.BankApplication;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer extends  AbstractServer{
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;

	public static Bank currentBank = null;
	public static Client currentClient = null;
	public static BankApplication bankApplication = new BankApplication();
	public static float amount;





	void run () {

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
			sendMessage("Connection successful, enter your name");
			// 4. The two parts communicate via the input and output streams
			do {
				try {
					message = (String) in.readObject();


				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("client> " + message);
				if (message.equals("bye")) {
					sendMessage("bye");
				}
				currentClient = null;
				BankImpl bankImp = new BankImpl();


				try {
					currentClient = bankImp.getClient(currentBank, message);
				} catch (ClientExistsException e) {
					e.printStackTrace();
				}


				if (currentClient == null) {
					System.out.println("Error!!! Client with such name was not found.");

					sendMessage("bye");
					return;
				}


				System.out.println("Client is selected: " + currentClient.toString() + "\n" +
						"select command ");


				sendMessage(" please, enter command: ");


				try {
					message = (String) in.readObject();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("client>" + message);

				while (!message.equals("bye")) {

					while (message.equals("balance")) {

						if (currentClient.getAccounts().isEmpty()) {
							sendMessage("Client: " + currentClient.getGender().getGenderPrefix() +
									currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getBankNumber());
							return;
						} else {
							sendMessage(currentClient.getAccounts().toString() + "Total balance: " + Float.toString(currentClient.getBalance())
									+ "\n select command ");

							try {
								message = (String) in.readObject();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							System.out.println("client>" + message);


							if (message.equals("balance")) {
								break;
							} else if (message.equals("bye")) {
								break;
							} else if (message.equals("withdraw")) {
								continue;
							}

						}
					}

					while (message.equals("withdraw")) {

						if (currentClient.getAccounts().isEmpty()) {
							sendMessage("Client: " + currentClient.getGender().getGenderPrefix() +
									currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getBankNumber());
							return;
						} else {
							sendMessage("Enter amount that you want to get: ");

							try {
								message = (String) in.readObject();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
							System.out.println("client>" + message);

							amount = Float.parseFloat(message);


							if (amount > (currentClient.getBalance()+ currentClient.getInitialOverdraft())) {
								System.out.println("Error!! amount more than can get ");
								sendMessage("bye");
								return;
							}
							try {
								currentClient.getActiveAccount().withdraw(amount);
								sendMessage("successful operation : \n new total balance:" +
										Float.toString(currentClient.getBalance()) + "\n please, select command ");

								try {
									message = (String) in.readObject();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								System.out.println("client>" + message);

								if (message.equals("balance")) {
									break;
								} else if (message.equals("bye")) {
									break;
								} else if (message.equals("withdraw")) {
									continue;
								}


							} catch (NotEnoughFundsException e) {
								e.printStackTrace();
							}

						}
					}

				}

				if (message.equals("bye")) {
					sendMessage("bye");

				}
			}

			while (!message.equals("bye"));

		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage (final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main (final String args[]) {

		AbstractServer abstractServer = new AbstractServer();
		abstractServer.initialize();           //initialized server with data
		currentBank = abstractServer.getCurrentBank();
		BankServer server = new BankServer();


		while (true) {

			server.run();
		}
	}
}