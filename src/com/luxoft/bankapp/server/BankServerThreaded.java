package com.luxoft.bankapp.server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by SCJP on 05.02.2015.
 */
public class BankServerThreaded {

    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public BankServerThreaded(int port, int poolSize) throws IOException{

            serverSocket = new ServerSocket(port);

        pool = Executors.newFixedThreadPool(poolSize);

    }




}
