import com.luxoft.bankapp.exception.NotEnoughFundsException;
import com.luxoft.bankapp.exception.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest {
    float amount;
    Client client;
   AbstractAccount account;
    AbstractAccount account2;
Bank bank;

Set<Account> accountSet = new HashSet<Account>();
Set<Client> clientSet = new HashSet<Client>();


    @Before
    public void setUp() throws Exception {

      accountSet.clear();
        clientSet.clear();
        amount = 100;
       account = new CheckingAccount(100, 200);
       account2 = new SavingAccount(200);


        accountSet.add(account);
        accountSet.add(account2);

        client = new Client();

        bank = new Bank();
        bank.setName("bank");

        client.setName("Ivan Ivanov");
        client.setCity("Kiev");
        client.setEmail("ewf@dsdf.com");
        client.setGender(Gender.MALE);
        client.setTelephoneNumber("0969876543");
        client.setInitialOverdraft(5000);

        client.setAccounts(accountSet);
        clientSet.add(client);
        bank.setClients(clientSet);
    }


    @Test
    public void testDeposit() throws Exception {
        ServiceFactory.getAccountImpl().deposit(amount, account, client);

        System.out.println("Deposit ");
        assertEquals(500, client.getBalance(), 0.0);

    }

    @Test
    public void testWithdraw() throws Exception {
        ServiceFactory.getAccountImpl().withdraw(amount, account, client);

        System.out.println("Withdraw ");
        assertEquals(300, client.getBalance(), 0.0);

    }

    @Test(expected = NotEnoughFundsException.class)
    public void testWithdrawNotEnoughFunds() throws Exception {

        System.out.println("test Withdraw throw NotEnoughFundsException ");
        ServiceFactory.getAccountImpl().withdraw(500, account2, client);



    }

    @Test(expected = OverDraftLimitExceededException.class)
    public void testWithdrawOverdraft() throws Exception {

        System.out.println("test Withdraw throw OverDraftLimitExceededException ");
        ServiceFactory.getAccountImpl().withdraw(500, account, client);
    }


    @Test
    public void testDecimalValue() throws Exception {
        System.out.println("DecimalValue ");

        account.setBalance((float) 200.103);
      float value =  ServiceFactory.getAccountImpl().decimalValue(account);


        assertEquals(200.10, value, 0.001 );

    }

    @Test
    public void testGetAccountById() throws Exception {

        ServiceFactory.getBankImpl().save(bank);
        Account account4 =  ServiceFactory.getAccountImpl().getAccountById(account.getId());
        System.out.println("Get Account By Id");
        assertTrue(TestService.isEquals(account4, account));
    }




    @Test
    public void testSetActiveAccount() throws Exception {
        ServiceFactory.getBankImpl().save(bank);
        ServiceFactory.getAccountImpl().setActiveAccount(client, account);

      Account account4 = client.getActiveAccount();
        System.out.println("Set Active Account");
        assertTrue(TestService.isEquals(account4, account));
    }



    @Test
    public void testAddAccount() throws Exception {

        Account account4 = new SavingAccount(1500);
        ServiceFactory.getBankImpl().save(bank);
   ServiceFactory.getAccountImpl().addAccount(client, account4);
        System.out.println("Add Account and get Client Accounts");

      List<Account> accounts =  ServiceFactory.getAccountImpl().getClientAccounts(client.getId());
        assertTrue(accounts.contains(account4));
    }


        @Test
    public void testTransfer() throws Exception {

        ServiceFactory.getBankImpl().save(bank);


        ServiceFactory.getAccountImpl().Transfer(account.getId(), account2.getId(), client.getId(), client.getId(), amount);



        System.out.println("Transfer ");
        assertEquals(account.getBalance()+ account2.getBalance(), client.getBalance(), 0.0);


        Account account1 = ServiceFactory.getAccountImpl().getAccountById(account.getId());
        Account account4 = ServiceFactory.getAccountImpl().getAccountById(account2.getId());

        assertEquals( 100 ,account1.getBalance(), 0.0);
        assertEquals( 300 ,account4.getBalance(), 0.0);
    }
}