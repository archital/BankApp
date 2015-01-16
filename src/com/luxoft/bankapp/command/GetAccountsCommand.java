package com.luxoft.bankapp.command;

/**
 * Created by acer on 15.01.2015.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get client's accounts ");
    }
}
