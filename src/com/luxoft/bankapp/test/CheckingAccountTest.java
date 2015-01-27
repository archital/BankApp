package com.luxoft.bankapp.test;

import com.luxoft.bankapp.expeption.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.CheckingAccount;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CheckingAccountTest {

CheckingAccount checkingAccount;

    @Before
    public void setUp() throws Exception {
checkingAccount = new CheckingAccount();
    }

    @Test
    public void testWithdraw() throws Exception {
        float sum = 10;

        checkingAccount.setOverdraft(10);
        checkingAccount.setBalance(50);

        checkingAccount.withdraw(sum);

        assertEquals(checkingAccount.getBalance(), 40, 0);


    }

    @Test(expected=OverDraftLimitExceededException.class)
    public void testWithdrawException() throws OverDraftLimitExceededException {

        float sum = 65;

        checkingAccount.setOverdraft(10);
        checkingAccount.setBalance(50);

        checkingAccount.withdraw(sum);


    }

    @Test
    public void testDecimalValue() throws Exception {

        float f = (float) 25.12;

        checkingAccount.setBalance((float) 25.1234);

             assertEquals(checkingAccount.decimalValue(), f, 0);

    }
}