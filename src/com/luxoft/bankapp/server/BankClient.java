package com.luxoft.bankapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by acer on 20.01.2015.
 */
public class BankClient {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	static final String SERVER = "localhost";

	void run () {
		try {
			// 1. creating a socket to connect to the server


			int portBank = 2004;

			requestSocket = new Socket(SERVER, 2004);
			System.out.println("Connected to localhost in port " + Integer.toString(portBank));
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {

					Scanner scanner = new Scanner(System.in);
					StringBuilder sb = new StringBuilder();


					message = (String) in.readObject();
					System.out.println("server>" + message);


					sb.append(scanner.nextLine().trim());

					message = sb.toString();
					sb.delete(0, sb.length());

					System.out.println("My name :" + message);
					sendMessage(message);
					message = (String) in.readObject();

					if (message.equals("bye")) {
						System.out.println("Client was not found");
						break;
					}
					System.out.println("server>" + message);  //enter command


					sb.append(scanner.nextLine().trim());

					message = sb.toString();
					sb.delete(0, sb.length());

					System.out.println("command is selected :" + message);
					sendMessage(message);

					while (!message.equals("bye")) {
						while (message.equals("withdraw")) {


							message = (String) in.readObject();
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);
							message = (String) in.readObject();
							System.out.println("server>!!!!" + message);


							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);


							if (message.equals("balance")) {
								break;
							} else if (message.equals("bye")) {
								break;
							} else if (message.equals("withdraw")) {
								continue;
							}

						}

						while (message.equals("balance")) {


							message = (String) in.readObject();
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							if (message.equals("balance")) {
								continue;
							} else if (message.equals("bye")) {
								break;
							} else if (message.equals("withdraw")) {
								break;
							}

						}
					}


				} catch (ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
				}
			} while (!message.equals("bye"));
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}


	void runBankRemote () {

		try {
			// 1. creating a socket to connect to the server

			int portBank = 2004;

			requestSocket = new Socket(SERVER, 2004);
			System.out.println("Connected to localhost in port " + Integer.toString(portBank));
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {

					Scanner scanner = new Scanner(System.in);
					StringBuilder sb = new StringBuilder();


					message = (String) in.readObject();
					System.out.println("server>" + message);


					sb.append(scanner.nextLine().trim());

					message = sb.toString();
					sb.delete(0, sb.length());


					System.out.println("command is selected :" + message);
					sendMessage(message);

					while (!message.equals("bye")) {

						while (message.equals("remove")) {

							message = (String) in.readObject();  //Enter client name thad you want to delete
							System.out.println("server> " + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							message = (String) in.readObject();  //Client removed!!  enter new command:  or "bye" command
							System.out.println("server>" + message);

							if (message.equals("bye")) {
								break;
							}
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							if (message.equals("bye")) {
								break;
							}
							if (message.equals("remove")) {
								continue;
							} else if (message.equals("add")) {
								break;
							}
						}

						while (message.equals("add")) {

							message = (String) in.readObject();  //Enter client name that you want to add
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							message = (String) in.readObject();  //enter gender to client  or "bye"
							System.out.println("server>" + message);

							if (message.equals("bye")) {
								break;
							}

							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());

							if (message.equals("m") || message.equals("f")) {
								sendMessage(message);
							} else {
								message = "bye";
								sendMessage(message);
								break;
							}

							message = (String) in.readObject();  //enter city to client name
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							message = (String) in.readObject();  //enter telephone to client
							if (message.equals("bye")) {
								break;
							}
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							message = (String) in.readObject();  //enter email to client
							if (message.equals("bye")) {
								break;
							}
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							message = (String) in.readObject();  //enter Overdraft to client
							if (message.equals("bye")) {
								break;
							}
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);


							message = (String) in.readObject();  //enter total balance to client
							if (message.equals("bye")) {
								break;
							}
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);

							message = (String) in.readObject();  //enter what Account TYPE you want to create to client  's' / 'c'
							if (message.equals("bye")) {
								break;
							}
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());

							if (message.equals("s") || message.equals("c")) {
								sendMessage(message);
							} else {
								message = "bye";
								sendMessage(message);
							}

							message = (String) in.readObject();  //enter new command
							System.out.println("server>" + message);
							sb.append(scanner.nextLine().trim());

							message = sb.toString();
							sb.delete(0, sb.length());
							sendMessage(message);


							if (message.equals("bye")) {
								break;
							}
							if (message.equals("remove")) {
								break;
							} else if (message.equals("add")) {
								continue;
							}
						}
					}


				} catch (ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
				}
			} while (!message.equals("bye"));
		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}


	void sendMessage (final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main (final String args[]) {
		BankClient client = new BankClient();
		//client.run();   // This method run connection to BankServer
		                  //you can enter and run command "balance" = to see client's balance
		                  // "withdraw" = to take money from account
		                  // "bye" = to exit


		  client.runBankRemote(); // This method run connection to BankRemoteOffice
		                          //you can enter and run command "remove" = to remove client
		                          // "add" = to add client
		                          // "bye" = to exit
	}

}
