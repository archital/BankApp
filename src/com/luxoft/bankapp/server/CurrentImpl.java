package com.luxoft.bankapp.server;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.ServiceFactory;


/**
 * Created by SCJP on 06.02.2015.
 */
public class CurrentImpl implements Current {
  private Client client;
  private Bank bank;



    @Override
    public Client getCurrentClient() {
        return client;
    }

    @Override
    public void setCurrentClient(Client client) {
     this.client = client;
    }

  @Override
  public Bank getCurrentBank () {
    return bank;
  }

  @Override
  public void setCurrentBank (Bank bank) {
    this.bank = bank;
  }
}
