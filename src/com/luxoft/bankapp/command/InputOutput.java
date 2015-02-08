package com.luxoft.bankapp.command;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by acer on 24.01.2015.
 */
public class InputOutput {

	ObjectInputStream is;
	ObjectOutputStream os;
	PrintStream standardOut;
	BufferedReader standardIn;
	boolean isStandard;
	private Lock lock = new ReentrantLock();


	public  InputOutput(InputStream is, OutputStream os) {
		this.is = (ObjectInputStream) is;
		this.os = (ObjectOutputStream) os;
		isStandard = false;
	}

    public InputOutput() {
        standardOut = System.out;
        standardIn = new BufferedReader(new InputStreamReader(System.in));
        isStandard = true;
    }



	public synchronized void println(Object object) {
		if (isStandard) {
			standardOut.println(object);
		} else {
			try {
				os.writeObject(object);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public synchronized String readln() {

		String result = "";
		if (isStandard) {
			try {
				lock.lock();
				result = standardIn.readLine();
				lock.unlock();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				result = is.readObject().toString();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}




