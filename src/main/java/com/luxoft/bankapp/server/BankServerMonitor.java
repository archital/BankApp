package com.luxoft.bankapp.server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Created by SCJP on 06.02.2015.
 */
public class BankServerMonitor  implements Runnable {



    private Socket connection = null;
    String message = null;

    @Override
    public void run() {


        while (true) {
            try {
                System.out.println("Number of waiting client is : " + BankServerThreaded.atomicInteger.get());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}