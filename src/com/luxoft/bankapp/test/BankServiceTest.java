package com.luxoft.bankapp.test;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BankServiceTest {

	Bank bank;

	@Before
	public void setUp() throws Exception {
		bank = new Bank();
		bank.setName("bank");
	}

	@Test
	public void testGetBankByName () throws Exception {
		ServiceFactory.getBankImpl().save(bank);
		Bank bank1 = ServiceFactory.getBankImpl().getBankByName(bank.getName());

		System.out.println("Get Bank By Name");
		assertTrue(TestService.isEquals(bank, bank1));

	}
}