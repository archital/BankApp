package com.luxoft.bankapp.command;

import com.luxoft.bankapp.expeption.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.AccountImpl;
import com.luxoft.bankapp.service.AccountService;
import com.luxoft.bankapp.service.ClientImpl;
import com.luxoft.bankapp.service.ClientService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by acer on 15.01.2015.
 */
public class TransferCommand implements Command {

    private InputOutput inOut;
    private Bank currentBank;
    private Client currentClient;
    private float amount = 0;
    private Account currentAccount;
    private  Integer accId = null;
    private Account currentAccountDeposit;
    private Integer accIdDeposit = null;


    public TransferCommand(InputOutput inputOutput, Bank currentBank, Client currentClient) {

        this.inOut = inputOutput;
        this.currentBank = currentBank;
        this.currentClient = currentClient;
    }

    public TransferCommand(InputOutput inOut) {

        this.inOut = inOut;
    }

    @Override
    public void execute() {


        ClientService clientService = new ClientImpl();
        AccountService accountService = new AccountImpl();


        if (currentBank == null) {
           inOut.println("Error!!! Current bank is undefined");
            return;
        }
        if (currentClient == null) {
            inOut.println("Error!!! Client with such name was not found.");
            return;
        }
        try {
            if (accountService.getClientAccounts(currentClient.getId()).isEmpty()) {
                inOut.println("Client: " + currentClient.getGender().getGenderPrefix() + currentClient.getName() + "haven't any accounts in Bank number " + currentBank.getId());
                return;
            }else{


                List<Account> accountList =  accountService.getClientAccounts(currentClient.getId());

                inOut.println("Enter account ID to make this account 'current': \n" +
                        accountList.toString());


                accId = Integer.parseInt(inOut.readln());
                currentAccount = accountService.getAccountById(accId);


                currentClient.setActiveAccount(currentAccount);

                inOut.println("Current account is selected:\n " +
                        currentAccount.toString()+
                        "\nEnter amount that you want to transfer from active account: ");
                amount = Float.parseFloat(inOut.readln());


                    inOut.println("Input client name whom you want to transfer money: ");
                String clientName = inOut.readln();

                 Client clientDeposit = null;
                try {
                    clientDeposit = clientService.findClientInDB(currentBank, clientName);
                } catch (ClientNotFoundException e) {
                    e.printStackTrace();
                }
                if (clientDeposit == null) {
                    inOut.println("Error!!! Client with such name was not found.");
                    return;
                } else {

                    if (accountService.getClientAccounts(clientDeposit.getId()).isEmpty()) {
                        inOut.println("Client: " + clientDeposit.getGender().getGenderPrefix() + clientDeposit.getName() + " haven't any accounts in Bank number " + currentBank.getId());
                        return;
                    }

                    List<Account> accounts =  accountService.getClientAccounts(clientDeposit.getId());

                    inOut.println("Enter account ID to make this account 'current': \n" +
                            accounts.toString());

                    accIdDeposit = Integer.parseInt(inOut.readln());
                    currentAccountDeposit = accountService.getAccountById(accIdDeposit);
                    clientDeposit.setActiveAccount(currentAccountDeposit);
                   accountService.Transfer(accId, accIdDeposit, currentClient.getId(), clientDeposit.getId(), amount);

                    inOut.println("Current account is selected:\n "+ currentAccountDeposit.toString()+ "\n"+
                   "Successful transfer, you can select new command" +
                            "\npress 'Enter' for CommanderServer ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void printCommandInfo() {
        inOut.println("Transfer command");
    }
}
