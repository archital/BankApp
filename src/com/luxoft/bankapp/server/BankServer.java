package com.luxoft.bankapp.server;

import com.luxoft.bankapp.BankApplication;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by acer on 20.01.2015.
 */
public class BankServer {
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    public static Bank currentBank = new Bank();
    public static Client currentClient = null;
    public static BankApplication bankApplication = new BankApplication();

    void run() {
        bankApplication.initialize();
        currentBank = bankApplication.getBank();
        try {
            // 1. creating a server socket

            try {
                providerSocket = new ServerSocket(2004, 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from "
                    + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection successful");
            // 4. The two parts communicate via the input and output streams
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("client>" + message);
                    Client currentClient = (Client) in.readObject();
                    if (message.equals("balance")) {
                        String balance = Float.toString(currentClient.getBalance());
                        sendMessage(balance);
                    }
                    if (message.equals("withdraw")) {
                        Client currentClient1 = (Client) in.readObject();
                        try {
                            currentClient1.getActiveAccount().withdraw(100);
                            String balance1 =   Float.toString(currentClient1.getBalance());
                            sendMessage(balance1);

                        } catch (NotEnoughFundsException e) {
                            e.printStackTrace();
                        }
                    }
                    if (message.equals("getinfo")) {
                        sendMessage("bankinfo");

                        new BankInfo(currentBank);
                    }
                    if (message.equals("bye"))
                        sendMessage("bye");
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
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

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        BankServer server = new BankServer();
        while (true) {
            server.run();
        }
    }
}