package com.luxoft.bankapp.command;

/**
 * Created by acer on 15.01.2015.
 */
public class DepositCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit to Client's account");
    }
}
