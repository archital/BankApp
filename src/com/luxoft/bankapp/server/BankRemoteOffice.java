package com.luxoft.bankapp.server;


import com.luxoft.bankapp.exception.ClientExistsException;
import com.luxoft.bankapp.exception.FeedException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
<<<<<<< HEAD
import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.model.Gender;
=======
import com.luxoft.bankapp.service.BankImpl;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.ClientImpl;
import com.luxoft.bankapp.service.ClientService;
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SCJP on 21.01.2015.
 */
public class BankRemoteOffice {

	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
    static String bankName = "My Bank";



	public static Bank currentBank = null;
	public static Client currentClient = null;
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
			sendMessage("Connection successful, enter command");
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


				while (!message.equals("bye")) {

					while (message.equals("remove")) {
						sendMessage("Enter client name that you want to delete ");

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						currentClient = null;
<<<<<<< HEAD
                        ClientService clientService = ServiceFactory.getClientImpl();
=======
                        ClientService clientService = new ClientImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3


						try {
							currentClient = clientService.getClient(currentBank, message);
						} catch (ClientExistsException e) {
							e.printStackTrace();
						}


						if (currentClient.equals(null)) {
							System.out.println("Error!!! This Client is already delete.");

							sendMessage("bye");
							return;
						}

                        try {
                            clientService.removeClient(currentClient, currentBank);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        sendMessage(" Client removed!!  enter new command: ");

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client>" + message);

						if (message.equals("bye")) {
							break;
						} else if (message.equals("add")) {
							break;
						} else if (message.equals("remove")) {
							continue;
						}
					}

					while (message.equals("add")) {
						sendMessage("Enter client name that you want to add ");

						try {
							try {
								message = (String) in.readObject();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println("client> " + message);

						currentClient = null;
<<<<<<< HEAD
						ClientService clientService = ServiceFactory.getClientImpl();
=======
						ClientService clientService = new ClientImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3


						try {
							currentClient = clientService.getClient(currentBank, message);
						} catch (ClientExistsException e) {
							e.printStackTrace();
						}


						if (!(currentClient == null)) {
							System.out.println("Error!!! This Client is already added.");

							sendMessage("bye");
							return;
						}

						Client client = new Client();
						client.setName(message);

						sendMessage("enter gender to client name " + client.getName());
						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						Pattern pattern = Pattern.compile("[MmFf]");
						Matcher matcher = pattern.matcher(message);
						if (!matcher.matches()) {
							System.out.println("Error!!! Illegal gender description");
							sendMessage("bye");
							return;
						}

						if (message.equals("m")) {
							client.setGender(Gender.MALE);
						}

						if (message.equals("f")) {
							client.setGender(Gender.FEMALE);
						}

						sendMessage("enter city to client name " +
								client.getGender().getGenderPrefix() + client.getName());

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						client.setCity(message);
						sendMessage("enter telephone to client name " +
								client.getGender().getGenderPrefix() + client.getName());

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						pattern = Pattern.compile("^\\(?|^\\+?(\\d{3}|\\d{5})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");
						matcher = pattern.matcher(message);
						if (!matcher.matches()) {
							System.out.println("Error!!! Illegal telephone number.");
							sendMessage("bye");
							return;
						}

						client.setTelephoneNumber(message);

						sendMessage("enter email to client name " +
								client.getGender().getGenderPrefix() + client.getName());

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						pattern = Pattern.compile("([a-zA-Z][\\w]*)@([a-zA-Z][\\w]*[.])*((net)|(com)|(org))");
						matcher = pattern.matcher(message);
						if (!matcher.matches()) {
							System.out.println("Error!!! Illegal e-mail address.");
							sendMessage("bye");
							return;
						}
						client.setEmail(message);

						sendMessage("enter Overdraft to client " +
								client.getGender().getGenderPrefix() + client.getName());

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						float overdraft = Float.parseFloat(message);

						if (overdraft < 0) {
							System.out.println("Error!!! Value must be positive. ");

							sendMessage("bye");
							return;
						}

						sendMessage("enter total balance to client " +
								client.getGender().getGenderPrefix() + client.getName());

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						float balance = (Float.parseFloat(message));

						if (balance < 0) {
							System.out.println("Error!!! Value must be positive. ");

							sendMessage("bye");
							return;
						}

						try {
							currentBank.addClient(client);
						} catch (ClientExistsException e) {
							e.printStackTrace();
						}

						sendMessage("enter what Account TYPE you want to create to client  's' / 'c' " +
								client.getGender().getGenderPrefix() + client.getName());

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client> " + message);

						if(message.equals("s")){


                            try {
                                client.setActiveAccount(client.createAccountWithOnlyType("s"));
	                            client.getActiveAccount().setBalance(balance);
                            } catch (FeedException e) {
                                e.printStackTrace();
                            }
                        } else if (message.equals("c")) {

                            try {
	                            CheckingAccount checkingAccount = (CheckingAccount) client.createAccountWithOnlyType("c");
	                            checkingAccount.setOverdraft(overdraft);
                                client.setActiveAccount(checkingAccount);
                            } catch (FeedException e) {
                                e.printStackTrace();
                            }
                        } else {
							message = "bye";
							sendMessage(message);
							return;
						}

						sendMessage(" Client is add!! " +
								client.toString()
								+ " enter new command: ");

						try {
							message = (String) in.readObject();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println("client>" + message);

						if (message.equals("bye")) {
							break;
						} else if (message.equals("add")) {
							continue;
						} else if (message.equals("remove")) {
							break;
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


<<<<<<< HEAD
        BankService bankService = ServiceFactory.getBankImpl();
=======
        BankService bankService = new BankImpl();
>>>>>>> c5258326ff7a4e2435eefad0db80b4034e1583e3


        try {
            currentBank = bankService.getBankByName(bankName);
        } catch (SQLException e) {
            e.printStackTrace();
        }


		BankRemoteOffice bankRemoteOffice = new BankRemoteOffice();


		while (true) {

			bankRemoteOffice.run();
		}
	}
}
