package com.luxoft.bankapp.server;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

/**
 * Created by SCJP on 06.02.2015.
 */
public interface Current {
   public Client getCurrentClient ();
    public void setCurrentClient(Client client);
    public Bank getCurrentBank ();
    public void setCurrentBank(Bank bank);
}
