package com.luxoft.bankapp.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.util.logging.*;

/**
 * Created by ${ArthurPopichenko} on 10.02.2015.
 */
public class LoggerTest {



	@Test
	public void testCreateLogConsole() throws Exception {
		FileInputStream configFile = new FileInputStream("C:\\Users\\acer\\IdeaProjects\\BankApplication\\src\\com\\luxoft\\bankapp\\resources\\logger_clients");

			Logger logger = Logger.getLogger(LoggerTest.class.getName());

		LogManager.getLogManager().readConfiguration(configFile);
		    logger.log(Level.FINEST, "Console Log ");

		configFile.close();
		}


	@Test
	public void testCreateLogToFile() throws Exception {
		FileInputStream configFile = new FileInputStream("C:\\Users\\acer\\IdeaProjects\\BankApplication\\src\\com\\luxoft\\bankapp\\resources\\logger_all");

		Logger logger = Logger.getLogger(LoggerTest.class.getName());

		LogManager.getLogManager().readConfiguration(configFile);
		logger.log(Level.INFO, "File log ");
		logger.log(Level.FINEST, "Should don't write to file ");

		configFile.close();
	}


}
