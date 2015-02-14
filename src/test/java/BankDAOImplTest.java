import com.luxoft.bankapp.dao.*;
import com.luxoft.bankapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BankDAOImplTest {

    Bank bank1;
    Set <Client> clientSet = new HashSet<Client>();
    Client client1;
    AbstractAccount account1;


    @Before
    public void initBanks() throws Exception {

        bank1 = new Bank();
        bank1.setName("bank");
         client1 = new Client();
        client1.setName("Ivan Ivanov");
        client1.setCity("Kiev");
        client1.setEmail("ewf@dsdf.com");
        client1.setGender(Gender.MALE);
        client1.setTelephoneNumber("0969876543");
        client1.setInitialOverdraft(5000);


       account1 = new CheckingAccount(50, 100);
        client1.addAccount(account1);




       clientSet.add(client1);
        bank1.setClients(clientSet);


    }

    @Test
    public void testClientInsert() throws Exception {
        ClientDAO clientDAO = DAOFactory.getClientDAO();


        bank1.setId(2);
       clientDAO.save(client1, bank1.getId());

        Client client2 = clientDAO.findClientByName(bank1, client1.getName());

        System.out.println("insert Client check");
        assertTrue(TestService.isEquals(client1, client2));
    }


    @Test
    public void testInsert() throws Exception {


        bank1.setId(null);
        BankDAO bankDAO = DAOFactory.getBankDAO();
        bankDAO.save(bank1);

        Bank bank2 = bankDAO.load(bank1.getName());

        System.out.println("insert bank");
        System.out.println(bank1.toString() + "\n" + bank1.getClients().toString());
        System.out.println(bank2.toString() + "\n" + bank2.getClients().toString());

        assertTrue(TestService.isEquals(bank1, bank2));
    }


    @Test
    public void testUpdate() throws Exception {

        bank1.setId(null);
        BankDAO bankDAO = DAOFactory.getBankDAO();
        bankDAO.save(bank1);

        // make changes to Bank

        Client client2 = new Client();
        client2.setName("Petia Petrov");
        client2.setCity("Moskov");
        client2.setEmail("ewf@dsdf.com");
        client2.setGender(Gender.MALE);
        client2.setTelephoneNumber("012346543");
        client2.setInitialOverdraft(2000);

        Account account2 = new CheckingAccount(50, 100);
        client2.addAccount(account2);


       bank1.getClients().add(client2);


        bankDAO.save(bank1);

      Bank bank2 = bankDAO.load(bank1.getName());


                System.out.println("update bank");
        System.out.println(bank1.toString() +"\n"+ bank1.getClients().toString());
        System.out.println(bank2.toString() +"\n"+ bank2.getClients().toString());

        assertTrue(TestService.isEquals(bank1, bank2));
    }


}