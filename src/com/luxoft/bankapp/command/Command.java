package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exception.ClientExistsException;

/**
 * Created by acer on 15.01.2015.
 */
public interface Command {
    void execute() throws ClientExistsException;

    void printCommandInfo();
}
