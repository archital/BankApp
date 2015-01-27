package com.luxoft.bankapp.test;

import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class SavingAccountTest {

    SavingAccount savingAccount;


    @Before
    public void setUp() throws Exception {
        savingAccount = new SavingAccount();
    }

    @Test
    public void testDecimalValue() throws Exception {

        float f = (float) 25.12;

        savingAccount.setBalance((float) 25.1234);

        assertEquals(savingAccount.decimalValue(), f, 0);


    }
}
