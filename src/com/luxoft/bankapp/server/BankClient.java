package com.luxoft.bankapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Arthur Popichenko on 20.01.2015.
 */
public class BankClient {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	static final String SERVER = "localhost";



	void run() {
		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket(SERVER, 2004);
			System.out.println("Connected to localhost in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {
					message = (String) in.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("server>" + message);

				if (!message.equals("bye")) {

					Scanner scanner = new Scanner(System.in);
					StringBuilder sb = new StringBuilder();

					sb.append(scanner.nextLine().trim());

					message = sb.toString();
					sb.delete(0, sb.length());
					sendMessage(message);
				}
				else {
					sendMessage("bye");
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


		client.run();
	}

}

