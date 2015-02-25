package com.luxoft.bankapp.command;


import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

/**
 * Created by acer on 15.01.2015.
 */
public interface Command {
    void execute();
    void printCommandInfo();
}
