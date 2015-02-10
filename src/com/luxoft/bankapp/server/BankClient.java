package com.luxoft.bankapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arthur Popichenko on 20.01.2015.
 */
public class BankClient {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	static final String SERVER = "localhost";
    private static final Logger logger = Logger.getLogger(BankClient.class.getName());


	void run() {
		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket(SERVER, 2004);
//			System.out.println("Connected to localhost in port 2004");
			logger.log(Level.FINEST, "Connected to localhost in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {
					message = (String) in.readObject();
				} catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, e.getMessage() + "Current Client wasn't found in DB ", e);
				}
			//	System.out.println("server>" + message);
                logger.log(Level.FINEST, "server>" + message);
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
			logger.log(Level.SEVERE, unknownHost.getMessage() + "You are trying to connect to an unknown host! ");
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			logger.log(Level.SEVERE,  ioException.getMessage() + " read/write information exception ");
		} finally {
			// 4: Closing connection
			try {
				logger.setLevel(Level.INFO);
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				logger.log(Level.SEVERE, ioException.getMessage() + " read/write information exception ");
			}
		}
	}





	void sendMessage (final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			logger.log(Level.FINEST, "client>" + msg);
		//	System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main (final String args[]) {
		BankClient client = new BankClient();
		logger.setLevel(Level.FINEST);

		client.run();
	}

}

