import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.BankClientMock;
import com.luxoft.bankapp.service.ServiceFactory;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by ${ArthurPopichenko} on 08.02.2015.
 */
public class BankServerThreadedTest {

	private String bankName = "My Bank";
	private String clientName = "Иван Иванов";



	@Test
	public void testClientsWorkingTime () throws Exception {


		Bank bank = ServiceFactory.getBankImpl().getBankByName(bankName);
		Client client = ServiceFactory.getClientImpl().findClientInDB(bank, clientName);

		ExecutorService executor = Executors.newFixedThreadPool(100);

		double amount = client.getBalance();
		long  clientsTime = 0;

			for (int i = 0; i < 1000; i++) {
				Future<Long> time =  executor.submit(new BankClientMock());
				System.out.println("Client "+(i+1)+ " work time "+ time.get());
			Thread.sleep(5);
				clientsTime = clientsTime + time.get();
			}
			executor.shutdown();
		Thread.sleep(100);
		client = ServiceFactory.getClientImpl().findClientInDB(bank, clientName);
		System.out.println("average time: " + clientsTime / 1000);

		double amount2 = client.getBalance();
		assertEquals(amount - 1000, amount2, 0);

	}


}
