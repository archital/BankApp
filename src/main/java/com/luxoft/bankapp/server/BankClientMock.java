package com.luxoft.bankapp.server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by ${ArthurPopichenko} on 08.02.2015.
 */
public class BankClientMock implements  Callable {

	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	static final String SERVER = "localhost";
    Lock lock = new ReentrantLock();



public synchronized void sendMessage (final String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}


	@Override
	public synchronized Long call () throws Exception {

          long time = System.currentTimeMillis();

		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket(SERVER, 2004);
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do {
				try {
					lock.lock();
					message = (String) in.readObject();
					lock.unlock();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			//	System.out.println("server>" + message);

				if (!message.equals("bye")) {

					message = "Иван Иванов";
					sendMessage(message);

					try {lock.lock();
						message = (String) in.readObject();
						lock.unlock();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					message = "2";
					sendMessage(message);

					try {lock.lock();
						message = (String) in.readObject();
						lock.unlock();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					message = "5";
					sendMessage(message);

					try {lock.lock();
						message = (String) in.readObject();
						lock.unlock();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}


					message = "1";
					sendMessage(message);


					try {lock.lock();
						message = (String) in.readObject();
						lock.unlock();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					message = "bye";
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

		return System.currentTimeMillis() - time;
	}

}
