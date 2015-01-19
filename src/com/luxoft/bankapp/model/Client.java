package com.luxoft.bankapp.model;

import com.luxoft.bankapp.expeption.NotEnoughFundsException;
import com.luxoft.bankapp.service.Gender;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SCJP on 14.01.2015.
 */
public class Client implements Account, Report {
    @Override
    public void printReport() {  //Вывести информацию о клиентах и всех его считать
        System.out.println("Client " +
                "name='" + this.gender.getGenderPrefix() +this.name  + " email address : "+ this.getEmail()+" telephone: "+
                this.getTelephoneNumber() +
                " , accounts = ");
        for(Report account : accounts){
            account.printReport();
        };
        System.out.println( "Total balance =" + getBalance() +", Active Account balance=" + activeAccount.getBalance());


    }
    private Gender gender;
    private String name = "";
    private String telephoneNumber = "";
    private String email = "";
    private List<Account> accounts = new ArrayList<Account>();
    private float initialOverdraft;
    private Account activeAccount;


    public Client() {
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addAccount (Account account){
        accounts.add(account);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Client(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }
    public Gender getGender() {
        return gender;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public float getBalance() {

        float balance = 0;

        for(Account account : accounts){
            balance += account.getBalance();
        }
        return balance;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    @Override
    public void deposit(float x) {
        float wd = x;
        activeAccount.deposit(wd);

    }

    @Override
    public void withdraw(float x) {
        float wd = x;
        try {
            activeAccount.withdraw(wd);
        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBalance(float balance) {

    }


    @Override
    public float decimalValue() { //Возвращает округленное значение баланса
       float f = getBalance();
        return  (Math.round(f));

    }

    public Client(String name, float initialOverdraft, Gender gender) {
        super();
        this.name = name;
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public void createCheckingAccount(float overdraft, float balance){
        CheckingAccount checkingAccount1 = null;
        if (overdraft >= 0) {
            checkingAccount1 = new CheckingAccount(overdraft, balance);
            addAccount(checkingAccount1);
        }  else {
            throw new  IllegalArgumentException("Overdraft must be more or equals 0");

        }
    }

    public void createSavingAccount(float minoverdraft, float balance){
       SavingAccount savingAccount1 = null;
        if (minoverdraft >=0) {
            savingAccount1 = new SavingAccount(minoverdraft, balance);
            addAccount(savingAccount1);
    }  else {
            throw new  IllegalArgumentException("Minimum overdraft must be more or equals 0");

        }
    }

    public void createAccount(String accountType, float minoverdraft, float overdraft, float balance) throws IllegalArgumentException{
        CheckingAccount checkingAccount1 = null;
        if (accountType.equals(checkingAccount1.getAccountType())) { if (overdraft >= 0) {
                 checkingAccount1 = new CheckingAccount(overdraft, balance);
            addAccount(checkingAccount1);
        }
        else {
            throw new  IllegalArgumentException("Overdraft must be more or equals 0");

        }
        }
        SavingAccount savingAccount1 = null;
        if (accountType.equals(savingAccount1.getAccountType())&&minoverdraft >=0) {
            savingAccount1 = new SavingAccount(minoverdraft, balance);
            addAccount(savingAccount1);
        }
        else {
            throw new  IllegalArgumentException("minoverdraft must be more or equals 0");

        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (Float.compare(client.initialOverdraft, initialOverdraft) != 0) return false;
        if (accounts != null ? !accounts.equals(client.accounts) : client.accounts != null) return false;
        if (activeAccount != null ? !activeAccount.equals(client.activeAccount) : client.activeAccount != null)
            return false;
        if (gender != client.gender) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (activeAccount != null ? activeAccount.hashCode() : 0);
        result = 31 * result + (initialOverdraft != +0.0f ? Float.floatToIntBits(initialOverdraft) : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Client name: ");
        stringBuilder.append(getGender().getGenderPrefix());
        stringBuilder.append(getName());
        stringBuilder.append(" email address :");
        stringBuilder.append(getEmail());
        stringBuilder.append(" telephone : ");
        stringBuilder.append(getTelephoneNumber());
        stringBuilder.append(" Total balance: ");
        stringBuilder.append(getBalance());
        stringBuilder.append(" Accounts: ");
                for (Account ac: accounts){
                    stringBuilder.append(ac.toString());
                    stringBuilder.append("\n");
        }
       return stringBuilder.toString();
    }
}