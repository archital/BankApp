package com.luxoft.bankapp.server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by SCJP on 05.02.2015.
 */
public class BankServerThreaded {

    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    volatile boolean running;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    private static final Logger logger = Logger.getLogger(BankServerThreaded.class.getName());

    public BankServerThreaded(int port, int poolSize) throws IOException{

            serverSocket = new ServerSocket(port);

        pool = Executors.newFixedThreadPool(poolSize);

    }

    public static void main(String[] args) throws IOException {


        Thread thread = new Thread(new BankServerMonitor());
        thread.setDaemon(true);
        thread.start();
        BankServerThreaded bankServerThreaded = new BankServerThreaded(2004, 1000);
        bankServerThreaded.serve();
    }


public void serve () {

  running = true;
    while (running) {
        try {
            logger.log(Level.INFO, "Waiting for connection");
            pool.execute(new ServerThread(serverSocket.accept()));
            atomicInteger.incrementAndGet();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage() + "I/O Exception  ", e);
        }
    }
}

}
