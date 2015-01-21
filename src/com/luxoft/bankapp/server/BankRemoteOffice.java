package com.luxoft.bankapp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by acer on 21.01.2015.
 */
public class BankRemoteOffice {
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

                    Scanner scanner = new Scanner(System.in);
                    StringBuilder sb = new StringBuilder();


                    message = (String) in.readObject();
                    System.out.println("server>" + message);


                    sb.append(scanner.nextLine().trim());

                    message = sb.toString();  //should enter "Office" to connect like "Remote Office"
                    sb.delete(0, sb.length());

                    System.out.println("My name :"+ message);
                    sendMessage(message);
                    message = (String) in.readObject();

                    if(message.equals("bye")) {
                        System.out.println( "Client was not found");
                        break;
                    }
                    System.out.println("server>" + message);  //enter command


                    sb.append(scanner.nextLine().trim());

                    message = sb.toString();
                    sb.delete(0, sb.length());

                    System.out.println("command is selected :"+ message);
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

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        BankClient client = new BankClient();
        client.run();
    }

}
