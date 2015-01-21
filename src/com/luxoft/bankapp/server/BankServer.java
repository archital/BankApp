package com.luxoft.bankapp.server;

import com.luxoft.bankapp.BankApplication;
import com.luxoft.bankapp.Listeners.ClientRegistrationListener;
import com.luxoft.bankapp.command.BankCommander;
import com.luxoft.bankapp.expeption.ClientExistsException;
import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
    public static float amount;


    static {
        // first client
        Account account1ForClient1 = new SavingAccount(100);
        Account account2ForClient1 = new CheckingAccount(2, 50);
        Client client1 = new Client();
        client1.setName("Peter");
        client1.setGender(Gender.MALE);
        client1.setEmail("qq@mail.ru");
        client1.setTelephoneNumber("+380953434243");
        client1.setCity("Dnepr");
        client1.setActiveAccount(account1ForClient1);

        // second client
        Account account1ForClient2 = new SavingAccount(20);
        Account account2ForClient2 = new CheckingAccount(4, 35);
        Client client2 = new Client();
        client2.setName("Ludmila");
        client2.setGender(Gender.FEMALE);
        client2.setEmail("qq@gmail.com");
        client2.setTelephoneNumber("+380952342243");
        client1.setCity("Lvov");
        client2.setActiveAccount(account1ForClient2);


        List<ClientRegistrationListener> listeners = new ArrayList();

        currentBank = new Bank(1);
        currentBank = new Bank(listeners);
        currentBank.setBankNumber(1);
        BankService bankService = new BankImpl();
        try {
            bankService.addClient(currentBank, client1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addClient(currentBank, client2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client1, account1ForClient1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client1, account2ForClient2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client2, account1ForClient2);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        try {
            bankService.addAccount(client2, account2ForClient1);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        System.out.println("///////////////Client1 toString////////////");
        System.out.println(client1.toString());
        System.out.println("///////////////Client2 toString////////////");
        System.out.println(client2.toString());
        currentClient = client1;

    }



    void run() { BankReport bankReport = new BankReport();
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

                    if (message.equals("Office")) {
                        System.out.println("client> " + message);

                        sendMessage(" Enter client, you want to find  ");
                        message = (String) in.readObject();

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

                        BankInfo bankInfo = new BankInfo(currentBank, currentClient);

                        try {
                            message = (String) in.readObject();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        System.out.println("client>" + message); //while = bye

                        while (!message.equals("bye")) {

                            while (message.equals("add")) {


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



                       ///конец Ремув Банка

                    }
                    }catch(IOException e){
                        e.printStackTrace();
                    }catch(ClassNotFoundException e){
                        e.printStackTrace();
                    } catch (ClientExistsException e) {
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
                    System.out.println("client>" + message); //while = bye

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