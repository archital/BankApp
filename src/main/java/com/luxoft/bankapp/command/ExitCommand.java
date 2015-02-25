package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.server.Current;

/**
 * Created by SCJP on 25.02.2015.
 */
public class ExitCommand extends AbstractCommand implements Command {

    private InputOutput ioStreams;


    public ExitCommand(InputOutput inputOutput) {
        this.ioStreams = inputOutput;

    }

    public ExitCommand() {
    }


    @Override
    public void execute() {
        ioStreams.println("bye");
        System.exit(0);
    }

    @Override
    public void printCommandInfo() {
        ioStreams.println("ExitCommand");
    }
}
