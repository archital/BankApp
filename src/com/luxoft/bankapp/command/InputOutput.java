package com.luxoft.bankapp.command;

import java.io.*;

/**
 * Created by acer on 24.01.2015.
 */
public class InputOutput {

	ObjectInputStream is;
	ObjectOutputStream os;
	PrintStream standardOut;
	BufferedReader standardIn;
	boolean isStandard;


	public  InputOutput(InputStream is, OutputStream os) {
		this.is = (ObjectInputStream) is;
		this.os = (ObjectOutputStream) os;
		isStandard = false;
	}



	public void println(Object object) {
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


	public String readln() {
		String result = "";
		if (isStandard) {
			try {
				result = standardIn.readLine();
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
