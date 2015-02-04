package com.luxoft.bankapp.model;

import com.luxoft.bankapp.annotation.annotation;
import com.luxoft.bankapp.expeption.FeedException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by SCJP on 14.01.2015.
 */
public class Client implements Report, Serializable {

    @annotation.NoDB    private Integer id = null;

    @annotation.NoDB private static final long serialVersionUID = 1L;


    private Gender gender;
    private String name = "";
    private String telephoneNumber = "";
    private String email = "";
    private Set<Account> accounts = new HashSet<Account>();
    private float initialOverdraft;
    @annotation.NoDB   private Account activeAccount;
    private String city = "";


    @Override
    public void printReport() {
        System.out.println("Client " +
                "name='" + this.gender.getGenderPrefix() + this.name + " email address : " + this.getEmail() + " telephone: " +
                this.getTelephoneNumber() +
                " , accounts = ");
        for (Report account : accounts) {
            account.printReport();
        }

        System.out.println("Total balance =" + getBalance() + ", Active Account balance=" + activeAccount.getBalance());


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client(String name) {
        this.name = name;
    }

    public void parseFeed(Map<String, String> feed) throws FeedException {
        String accountType = feed.get("accountType");
        Account acc = findAccountByItsType(accountType);
        setActiveAccount(acc);

        if ("m".equals(feed.get("gender"))) { //CHECK GENDER
            setGender(Gender.MALE);
        }
        if ("F".equals(feed.get("gender"))) {
            setGender(Gender.FEMALE);
        }


        acc.parseFeed(feed);

    }


    private Account findAccountByItsType(String accountType) throws FeedException {
        for (Account acc : accounts) {

            if (acc.getClass().equals(CheckingAccount.class) && ("c".equals(accountType))) {
                return acc;
            }
            if (acc.getClass().equals(SavingAccount.class) && ("s".equals(accountType))) {
                return acc;
            }

        }

        return createAccountWithOnlyType(accountType);
    }



    public Account createAccountWithOnlyType(String accountType) throws FeedException {
        Account acc;
        if ("s".equals(accountType)) {
            acc = new SavingAccount();
        } else if ("c".equals(accountType)) {
            acc = new CheckingAccount();
        } else {
            throw new FeedException("Account type not found " + accountType);
        }
        accounts.add(acc);
        return acc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    public void addAccount(Account account) {
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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public float getBalance() {

        float balance = 0;

        for (Account account : accounts) {
            balance += account.getBalance();
        }
        return balance;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }


    public Client(String name, float initialOverdraft, Gender gender) {

        this.name = name;
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (Float.compare(client.initialOverdraft, initialOverdraft) != 0) return false;
        if (accounts != null ? !accounts.equals(client.accounts) : client.accounts != null) return false;
        if (gender != client.gender) return false;
        if (telephoneNumber != client.telephoneNumber) return false;
        if (city != client.city) return false;
        if (email != client.email) return false;
        if (!name.equals(client.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (activeAccount != null ? activeAccount.hashCode() : 0);
        result = 31 * result + (initialOverdraft != +0.0f ? Float.floatToIntBits(initialOverdraft) : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (telephoneNumber != null ? telephoneNumber.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }




    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID ");
        stringBuilder.append(getId());
        stringBuilder.append(" name: ");
        stringBuilder.append(getGender().getGenderPrefix());
        stringBuilder.append(getName());
        stringBuilder.append(" email address :");
        stringBuilder.append(getEmail());
        stringBuilder.append(" telephone : ");
        stringBuilder.append(getTelephoneNumber());
        stringBuilder.append(" City: ");
        stringBuilder.append(getCity());
        stringBuilder.append(" Total balance: ");
        stringBuilder.append(getBalance());
        stringBuilder.append(" initial overdraft: ");
        stringBuilder.append(getInitialOverdraft());
        stringBuilder.append(" Accounts: ");
        for (Account ac : accounts) {
            stringBuilder.append(ac.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


}